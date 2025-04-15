import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-registro-form',
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule],
  templateUrl: './registro-form.component.html',
  styleUrls: ['./registro-form.component.css']
})
export class RegistroFormComponent {
  form: FormGroup;
  mensaje = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      apellidos: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmar: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.form.invalid || this.form.value.password !== this.form.value.confirmar) {
      this.mensaje = 'Corrige los errores antes de continuar.';
      return;
    }

    const { nombre, apellidos, email, password } = this.form.value;

    this.authService.registrarCliente({ nombre, apellidos, email, password }).subscribe({
      next: () => {
        this.router.navigate(['/access/candidato']);
      },
      error: err => {
        console.error(err);
        this.mensaje = 'Hubo un problema al registrarse.';
      }
    });
  }
}
