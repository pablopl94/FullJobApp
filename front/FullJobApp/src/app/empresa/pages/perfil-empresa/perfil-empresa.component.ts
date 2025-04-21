import { Component, inject } from '@angular/core';
import { EmpresaService } from '../../../core/services/empresa.service';
import { IEmpresa } from '../../../core/interfaces/IEmpresa';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-perfil-empresa',
  imports: [FormsModule],
  templateUrl: './perfil-empresa.component.html',
  styleUrl: './perfil-empresa.component.css'
})
export class PerfilEmpresaComponent {


  empresa!: IEmpresa;
  backupEmpresa!: IEmpresa; // Copia para revertir si se cancela
  modoEdicion = false;

  empresaService = inject(EmpresaService);

  constructor() {}

  ngOnInit(): void {
    this.empresaService.getDetallesEmpresaAutenticada().subscribe({
      next: (data) => {
        this.empresa = data;
        this.backupEmpresa = { ...data }; // Guardamos una copia para cancelar cambios
      }
    });
  }

  // Guardar cambios con SweetAlert de éxito
  guardarCambios(): void {
    this.empresaService.actualizarEmpresa(this.empresa).subscribe({
      next: (actualizada) => {
        this.empresa = actualizada;
        this.backupEmpresa = { ...actualizada };
        this.modoEdicion = false;
        Swal.fire({
          icon: 'success',
          title: '¡Perfil actualizado!',
          text: 'Los datos de la empresa se han guardado correctamente.',
          timer: 2500,
          showConfirmButton: false
        });
      },
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudo actualizar el perfil. Inténtalo más tarde.',
        });
      }
    });
  }

  // Cancelar cambios y restaurar copia
  cancelarEdicion(): void {
    this.empresa = { ...this.backupEmpresa };
    this.modoEdicion = false;
    Swal.fire({
      icon: 'info',
      title: 'Cambios cancelados',
      text: 'Se han descartado los cambios.',
      timer: 2000,
      showConfirmButton: false
    });
  }
}