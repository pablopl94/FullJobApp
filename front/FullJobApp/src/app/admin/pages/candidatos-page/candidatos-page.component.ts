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
    this.obtenerCandidatos();
  }

  obtenerCandidatos(): void {
    this.usuarioService.getCandidatos().subscribe({
      next: (data) => {
        this.usuarios = data.filter((u) => u.rol === 'CLIENTE');
      },
      error: () => {
        this.usuarios = [];
      },
    });
  }
  suspenderUsuario(usuario: IUsuario): void {
    if (usuario.enabled === 1) {
      // Está activo → desactivar
      this.usuarioService.desactivar(usuario.email).subscribe(() => {
        usuario.enabled = 0;
      });
    } else {
      // Está inactivo → activar
      this.usuarioService.activar(usuario.email).subscribe(() => {
        usuario.enabled = 1;
      });
    }
  }
}
