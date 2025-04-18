import { Component } from '@angular/core';
import { UsuarioService } from '../../../core/services/usuario.service';
import { IUsuario } from '../../../core/interfaces/iusuario';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdministradoresActionComponent } from '../../components/administradores-action/administradores-action.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-administradores-page',
  standalone: true,
  imports: [CommonModule, FormsModule, AdministradoresActionComponent,RouterLink],
  templateUrl: './administradores-page.component.html',
  styleUrl: './administradores-page.component.css',
})
export class AdministradoresPageComponent {
  usuarios: IUsuario[] = [];
  mostrarFormulario = false;
  administradorSeleccionado: IUsuario | null = null;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    // Llamamos al método que carga los candidatos (lo llena en el BehaviorSubject)
    this.usuarioService.getCandidatos();

    // Nos suscribimos al observable candidatos$
    this.usuarioService.candidatos$.subscribe({
      next: (data: IUsuario[]) => {
        // Filtramos solo los usuarios con rol ADMON
        this.usuarios = data.filter((u: IUsuario) => u.rol === 'ADMON');
      },
      error: () => {
        this.usuarios = [];
      },
    });
  }

}