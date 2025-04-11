import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';


@Component({
  standalone: true,
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  imports: [CommonModule, RouterModule]
})
export class NavbarComponent implements OnInit {

  private readonly authService: AuthService = inject(AuthService);
  role: string = '';

  ngOnInit(): void {
    this.role = this.authService.obtenerRol();
    // Para depuración
    console.log('[Navbar] Rol detectado:', this.role);
  }

  logOut(): void {
    this.authService.logout();
  }
}

