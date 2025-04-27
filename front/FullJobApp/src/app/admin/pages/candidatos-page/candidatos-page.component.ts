import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioService } from '../../../core/services/usuario.service';
import { FormsModule } from '@angular/forms';
import { IUsuario } from '../../../core/interfaces/iusuario';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-candidatos-page',
  templateUrl: './candidatos-page.component.html',
  imports: [CommonModule, FormsModule],
})
export class CandidatosPageComponent implements OnInit {
 
  usuarios: IUsuario[] = [];

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    // Llamamos una vez al backend para cargar candidatos
    this.usuarioService.getCandidatos();

    // Nos suscribimos al observable para recibir los datos
    this.usuarioService.candidatos$.subscribe({
      next: (data: IUsuario[]) => {
        console.log(data);
        this.usuarios = data.filter((u: IUsuario) => u.rol === 'CLIENTE');
      },
      error: () => {
        this.usuarios = [];
      },
    });
  }

  suspenderUsuario(usuario: IUsuario): void {
    if (usuario.enabled === 1) {
      // Si está activo → lo desactivamos
      this.usuarioService.desactivar(usuario.email).subscribe(() => {
        usuario.enabled = 0;

        Swal.fire({
          icon: 'warning',
          title: 'Usuario suspendido',
          text: `El usuario ${usuario.nombre} ha sido suspendido.`,
          timer: 2000,
          showConfirmButton: false
        });
      });
    } else {
      // Si está desactivado → lo activamos  COMENTARIOS
      this.usuarioService.activar(usuario.email).subscribe(() => {
        usuario.enabled = 1;

        Swal.fire({
          icon: 'success',
          title: 'Usuario activado',
          text: `El usuario ${usuario.nombre} ha sido reactivado.`,
          timer: 2000,
          showConfirmButton: false
        });
      });
    }
  }
}