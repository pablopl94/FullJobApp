import { Component, inject, Input } from '@angular/core';
import { ICategoria } from '../../../core/interfaces/ICategoria';
import { CategoriasService } from '../../../core/services/categorias.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-categoria-actions',
  imports: [],
  templateUrl: './categoria-actions.component.html',
  styleUrl: './categoria-actions.component.css'
})
export class CategoriaActionsComponent {
  @Input() categoria!: ICategoria;
  categoriaService = inject(CategoriasService);

  editar(): void {
    Swal.fire({
      title: 'Modificar Categoría',
      html: `
        <input id="nombre" class="swal2-input" placeholder="Nombre" value="${this.categoria.nombre}">
        <input id="descripcion" class="swal2-input" placeholder="Descripción" value="${this.categoria.descripcion}">
      `,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Actualizar',
      preConfirm: () => {
        const nombre = (document.getElementById('nombre') as HTMLInputElement).value.trim();
        const descripcion = (document.getElementById('descripcion') as HTMLInputElement).value.trim();

        if (!nombre || !descripcion) {
          Swal.showValidationMessage('Todos los campos son obligatorios');
          return;
        }

        return { nombre, descripcion };
      }
    }).then(result => {
      if (result.isConfirmed && result.value) {
        const actualizada: ICategoria = {
          ...this.categoria,
          ...result.value
        };

        this.categoriaService.actualizarCategoria(this.categoria.idCategoria, actualizada).subscribe({
          next: () => {
            Swal.fire('Actualizada', 'Categoría actualizada correctamente', 'success')
              .then(() => location.reload()); // recarga la lista
          },
          error: (err) => {
            console.error('Error al actualizar categoría:', err);
            Swal.fire('Error', err.error || 'No se pudo actualizar la categoría', 'error');
          }
        });
      }
    });
  }

  eliminar(): void {
    Swal.fire({
      title: '⚠️ ¡Advertencia Importante!',
      html: `
        <strong>¿Estás seguro de eliminar esta categoría?</strong><br><br>
        <span style="color: red; font-weight: bold;">
          Si continúas, se eliminarán <u>TODAS las vacantes</u> y 
          <u>solicitudes asociadas</u> a esta categoría. <br><br>
          ¡Esta acción no se puede deshacer!
        </span>
      `,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar todo',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.categoriaService.eliminarCategoria(this.categoria.idCategoria).subscribe({
          next: (mensaje) => {
            Swal.fire('Eliminada', mensaje, 'success')
              .then(() => location.reload());
          },
          error: (err) => {
            console.error('Error al eliminar categoría:', err);
            Swal.fire('Error', err.error || 'No se pudo eliminar la categoría', 'error');
          }
        });
      }
    });
  }
  
}