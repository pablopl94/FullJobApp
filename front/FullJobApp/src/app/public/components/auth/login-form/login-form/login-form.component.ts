import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-login-form',
  standalone: true,
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css',
})
export class LoginFormComponent {

  //Input en el definimos si el formulario es para empresa o candidato
  @Input() tipo: 'empresa' | 'candidato' = 'candidato';

  // Getter para mostar un titulo distinto dependiendo del rol
  get titulo(): string {
    return this.tipo === 'empresa'
      ? 'Accede como Empresa'
      : 'Accede como Candidato';
  }

  // Metodo del login a implementar
  onLogin() {
    throw new Error('Method not implemented.');
    }
}
