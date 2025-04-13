
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { FooterComponent } from "../../../components/footer/footer.component";
import { NavbarComponent } from "../../../components/navbar/navbar.component";


@Component({
  standalone: true,
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css'],
  imports: [RouterModule, FooterComponent, NavbarComponent]
})
export class AdminPageComponent {

  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.email, this.password).subscribe({
      next: (response: any) => {
        const token = response.token;
        const usuario = response.usuario;

        this.authService.guardarUsuarioYToken(token, usuario); // Guardar token y usuario en el localStorage

        // Redirigir según el rol del usuario
        if (this.authService.obtenerRol() === 'ADMON') {
          this.router.navigate(['/admin']); // Redirige al panel de administración si el rol es 'ADMON'
        } else {
          this.router.navigate(['/']); // O a una página por defecto si no es administrador
        }
      },
      error: (err) => {
        this.errorMessage = 'Credenciales incorrectas';
        console.error('Error de login:', err);
      }
    });
  }
}
