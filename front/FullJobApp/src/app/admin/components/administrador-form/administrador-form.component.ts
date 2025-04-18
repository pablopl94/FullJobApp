import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import {FormBuilder,FormControl,FormGroup,ReactiveFormsModule,Validators,} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { IUsuario } from '../../../core/interfaces/iusuario';
import { UsuarioService } from '../../../core/services/usuario.service';
import { AuthService } from '../../../core/services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { IAdminRegistro } from '../../../core/interfaces/IAdminRegistro';

@Component({
  selector: 'app-administrador-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './administrador-form.component.html',
  styleUrls: ['./administrador-form.component.css'],
})
export class AdministradorFormComponent implements OnInit {
  
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private usuarioService = inject(UsuarioService);

  form: FormGroup;
  tipo: string;
  emailParam: string | null = null;

  // Constructor con definición del formulario
  constructor() {
    this.tipo = '';
    this.form = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      nombre: new FormControl('', Validators.required),
      apellidos: new FormControl('', Validators.required),
      password: new FormControl('', [Validators.required, Validators.minLength(8)])
    });
  }

  // Al iniciar, comprobamos si estamos en modo edición
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const email = params['email'];
      if (email) {
        this.tipo = 'Modificar';
        this.emailParam = email;
        this.cargarAdmin(email);
      } else {
        this.tipo = 'Alta';
      }
    });
  }

  // Cargar datos del admin a editar
  cargarAdmin(email: string): void {
    this.usuarioService.obtenerUsuario(email).subscribe({
      next: (admin) => {
        this.form.patchValue(admin);
        this.form.get('password')?.disable(); // ocultamos password
      },
      error: (error) => {
        console.error('[cargarAdmin] Error al obtener administrador:', error);
        Swal.fire({
          icon: 'error',
          title: 'Error al cargar administrador',
          confirmButtonColor: '#f4c542'
        }).then(() => this.router.navigate(['/admin/administradores']));
      }
    });
  }

  // Validaciones reutilizables
  checkControl(controlName: string, error: string): boolean {
    const control = this.form.get(controlName);
    return !!(control && control.touched && control.hasError(error));
  }

  // Enviar formulario (crear o actualizar)
  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const admin: IAdminRegistro = this.form.getRawValue(); // incluye campos deshabilitados

    // Si estamos en modo edición, aseguramos incluir la contraseña manualmente
    if (this.emailParam) {
      const passwordValor = this.form.get('password')?.value;
      if (passwordValor) {
        admin.password = passwordValor;
      } else {
        admin.password = '********'; // ocultamos la contraseña
      }
    }

    console.log('[onSubmit] Payload enviado:', admin);

    if (this.emailParam) {
      // Modo editar
      this.usuarioService.actualizarAdmin(this.emailParam, admin).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: 'Administrador actualizado correctamente',
            confirmButtonColor: '#f4c542'
          }).then(() => this.router.navigate(['/admin/administradores']));
        },
        error: (error) => {
          console.error('[onSubmit] Error al actualizar:', error);
          Swal.fire({
            icon: 'error',
            title: 'Error al actualizar administrador',
            confirmButtonColor: '#f4c542'
          });
        }
      });
    } else {
      // Modo alta
      this.usuarioService.registrarAdmin(admin).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: 'Administrador registrado correctamente',
            confirmButtonColor: '#f4c542'
          }).then(() => this.router.navigate(['/admin/administradores']));
        },
        error: (error) => {
          console.error('[onSubmit] Error al registrar:', error);
          Swal.fire({
            icon: 'error',
            title: 'Error al registrar administrador',
            confirmButtonColor: '#f4c542'
          });
        }
      });
    }
  }
}