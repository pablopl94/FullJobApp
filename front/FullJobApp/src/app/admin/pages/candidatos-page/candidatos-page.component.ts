import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IUsuario } from '../../../core/interfaces/IUsuario';
import { UsuarioService } from '../../../core/services/usuario.service';
import { FormsModule } from '@angular/forms';

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

  suspenderUsuario(email: string): void {
    this.usuarioService.desactivarUsuario(email).subscribe(() => {
      // Opcional: actualizar estado localmente
      const usuario = this.usuarios.find(u => u.email === email);
      if (usuario) {
        usuario.enabled = 0;
      }
    });
  }
  
}
