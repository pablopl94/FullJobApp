import { Component,inject,Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { UsuarioService } from '../../../core/services/usuario.service';
import { IUsuario } from '../../../core/interfaces/iusuario';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-administradores-action',
  imports: [RouterLink, NgClass],
  templateUrl: './administradores-action.component.html',
  styleUrl: './administradores-action.component.css'
})
export class AdministradoresActionComponent {
  
  @Input() usuario!: IUsuario;  // Recibe todo el objeto del usuario

  private usuarioService = inject(UsuarioService);

  cambiarEstado(): void {
    const esActivo = this.usuario.enabled === 1;
    const accion = esActivo ? 'desactivar' : 'activar';
    const textoConfirmacion = esActivo
      ? '¿Estás seguro de que deseas desactivar este administrador?'
      : '¿Quieres volver a activarlo?';

    Swal.fire({
      title: 'Confirmar acción',
      text: textoConfirmacion,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: `Sí, ${accion}`,
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#f4c542',
      cancelButtonColor: '#6c757d'
    }).then((result) => {
      if (result.isConfirmed) {
        const servicio = esActivo
          ? this.usuarioService.desactivar(this.usuario.email)
          : this.usuarioService.activar(this.usuario.email);

        servicio.subscribe({
          next: () => {
            Swal.fire(
              'Actualizado',
              `El administrador fue ${esActivo ? 'desactivado' : 'activado'} correctamente.`,
              'success'
            );
          },
          error: () => {
            Swal.fire(
              'Error',
              `No se pudo ${accion} al administrador.`,
              'error'
            );
          }
        });
      }
    });
  }
}