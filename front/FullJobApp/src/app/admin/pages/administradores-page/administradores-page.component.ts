import { Component } from '@angular/core';
import { UsuarioService } from '../../../core/services/usuario.service';
import { IUsuario } from '../../../core/interfaces/iusuario';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdministradoresActionComponent } from '../../components/administradores-action/administradores-action.component';
import { AdministradorFormComponent } from '../../components/administrador-form/administrador-form.component';

@Component({
  selector: 'app-administradores-page',
  standalone: true,
  imports: [CommonModule, FormsModule, AdministradoresActionComponent, AdministradorFormComponent],
  templateUrl: './administradores-page.component.html',
  styleUrl: './administradores-page.component.css',
})
export class AdministradoresPageComponent {
  usuarios: IUsuario[] = [];
  mostrarFormulario = false;
  administradorSeleccionado: IUsuario | null = null;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargarAdministradores();
  }

  cargarAdministradores(): void {
    this.usuarioService.getCandidatos().subscribe({
      next: (data) => {
        this.usuarios = data.filter((u) => u.rol === 'ADMON');
      },
      error: () => {
        this.usuarios = [];
      },
    });
  }

  nuevaAlta() {
    this.administradorSeleccionado = null;
    this.mostrarFormulario = true;
  }

  editarAdministrador(usuario: IUsuario) {
    this.administradorSeleccionado = usuario;
    this.mostrarFormulario = true;
  }
  

  eliminarAdministrador(email: string): void {
    if (confirm(`¿Seguro que quieres eliminar a ${email}?`)) {
      this.usuarioService.eliminarUsuario(email).subscribe(() => {
        this.cargarAdministradores(); // Recarga la lista actualizada
      });
    }
  }

  onFormularioGuardado() {
    this.mostrarFormulario = false;
    this.cargarAdministradores();
  }
}
