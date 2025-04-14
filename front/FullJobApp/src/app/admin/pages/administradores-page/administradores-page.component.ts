import { Component } from '@angular/core';
import { UsuarioService } from '../../../core/services/usuario.service';
import { IUsuario } from '../../../core/interfaces/IUsuario';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-administradores-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './administradores-page.component.html',
  styleUrl: './administradores-page.component.css'
})
export class AdministradoresPageComponent {

  usuarios: IUsuario[] = [];
  
    constructor(private usuarioService: UsuarioService) {}
  
    ngOnInit(): void {
      this.obtenerCandidatos();
    }
  
    obtenerCandidatos(): void {
      this.usuarioService.getCandidatos().subscribe({
        next: (data) => {
          this.usuarios = data.filter((u) => u.rol === 'ADMON');
        },
        error: () => {
          this.usuarios = [];
        },
      });
    }
}
