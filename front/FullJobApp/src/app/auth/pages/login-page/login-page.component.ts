import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from '../../../public/components/auth/login-form/login-form.component';


@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, LoginFormComponent],
  template: `<app-login-form tipo="candidato" />`,
})
export class LoginPageComponent {}
