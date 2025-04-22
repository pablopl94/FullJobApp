import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';
import { IClienteRegistro } from '../../../../core/interfaces/IClienteRegistro';
import Swal from 'sweetalert2';

@Component({
  standalone: true,
  selector: 'app-registro-form',
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule],
  templateUrl: './registro-form.component.html',
  styleUrls: ['./registro-form.component.css']
})
export class RegistroFormComponent {
  
  // Formulario reactivo
  form: FormGroup;

  // Mensaje de error para mostrar debajo del botón
  mensaje = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    // Definimos los campos y validaciones del formulario
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      apellidos: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmar: ['', Validators.required]
    });
  }

  // Se ejecuta al cargar el componente
  ngOnInit(): void {
    // No hace falta lanzar ningún error aquí
  }

  // Al hacer submit
  onSubmit(): void {
    // Si el formulario es inválido o las contraseñas no coinciden
    if (this.form.invalid || this.form.value.password !== this.form.value.confirmar) {
      this.mensaje = 'Corrige los errores antes de continuar.';
      return;
    }

    // Preparamos los datos del cliente a registrar
    const cliente: IClienteRegistro = {
      nombre: this.form.value.nombre,
      apellidos: this.form.value.apellidos,
      email: this.form.value.email,
      password: this.form.value.password
    };

    // Llamamos al servicio para registrar el cliente
    this.authService.registrarCliente(cliente).subscribe({
      // Si todo va bien
      next: () => {
        // ✅ Enviar correo mediante webhook de n8n
        this.authService.emailLogin(cliente.email, cliente.nombre).subscribe({
          next: () => console.log('✅ Correo enviado con éxito desde n8n'),
          error: (err) => console.error('❌ Error al enviar correo desde n8n:', err)
        });
        // Mostramos un mensaje bonito con SweetAlert
        Swal.fire({
          title: '¡Registro completado!',
          text: 'Te has registrado correctamente.',
          background: '#FAC402', 
          color: '#474747',       
          confirmButtonColor: '#fffff', // fondo del botón (gris oscuro)
          confirmButtonText: '¡Entendido!',
          customClass: {
            confirmButton: 'text-white', // letras blancas
            popup: 'rounded-3 shadow'
          }
        }).then(() => {
          this.router.navigate(['/access/candidato']);
        });        
      },

      // Si hay error desde el backend
      error: (err: any) => {
        console.error('Error recibido del backend:', err);

        // Intentamos extraer el mensaje personalizado
        const mensajeBackend = err.error?.message || err.error;
        if (typeof mensajeBackend === 'string') {
          const match = mensajeBackend.match(/"(.*?)"/); // Extrae lo que está entre comillas
          this.mensaje = match ? match[1] : mensajeBackend;
        } else {
          this.mensaje = 'Hubo un problema al registrarse.';
        }
      }
    });
  }
}