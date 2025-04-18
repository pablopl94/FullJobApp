import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioService } from '../../../core/services/usuario.service';
import { FormsModule } from '@angular/forms';
import { IUsuario } from '../../../core/interfaces/iusuario';

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
      });
    } else {
      // Si está desactivado → lo activamos
      this.usuarioService.activar(usuario.email).subscribe(() => {
        usuario.enabled = 1;
      });
    }
  }
}