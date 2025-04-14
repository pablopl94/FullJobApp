import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent {

 @Input() tipo: 'candidato' | 'empresa' | 'admin' = 'candidato';
  loginForm: FormGroup;
  @Input() parent: string = '';

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  get titulo(): string {
    return this.tipo === 'empresa' ? 'Accede como Empresa' : 'Accede como Candidato';
  }

  onLogin() {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
  
      this.auth.login(email, password).subscribe({
        next: (res: any) => {
          const token = res.token;
  
          const usuario = {
            email: res.email,
            nombre: res.nombre,
            apellidos: res.apellidos,
            rol: res.rol,
            enabled: res.enabled,
            fechaRegistro: res.fechaRegistro,
          };
  
          // Validar que el rol coincide con el tipo del formulario
          const rolFormulario = this.tipo === 'admin' ? 'ADMON'
                            : this.tipo === 'empresa' ? 'EMPRESA'
                            : 'CLIENTE';
  
          if (usuario.rol !== rolFormulario) {
            alert('No tienes permiso para acceder desde esta página');
            return;
          }
  
          // Guardamos token y usuario
          this.auth.guardarUsuarioYToken(token, usuario);
  
          // Redirigir según rol
          if (usuario.rol === 'ADMON') this.router.navigate(['/admin']);
          else if (usuario.rol === 'EMPRESA') this.router.navigate(['/empresa']);
          else if (usuario.rol === 'CLIENTE') this.router.navigate(['/candidato']);
          else this.router.navigate(['/']);
        },
        error: (err: any) => {
          alert('Login incorrecto. Verifica tus credenciales.');
          console.error(err);
        }
      });
    }
  }
}
