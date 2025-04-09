import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:9007/auth'; // tu backend

  constructor(private http: HttpClient, private router: Router) {}

  login(email: string, password: string) {
    return this.http.post(`${this.baseUrl}/login`, { email, password });
  }

  guardarUsuarioYToken(token: string, usuario: any) {
    localStorage.setItem('token', token);
    localStorage.setItem('usuario', JSON.stringify(usuario));
  }

  obtenerToken(): string | null {
    return localStorage.getItem('token');
  }

  obtenerUsuario(): any {
    const user = localStorage.getItem('usuario');
    return user ? JSON.parse(user) : null;
  }

  estaLogueado(): boolean {
    return !!this.obtenerToken();
  }

  logout() {
    //Usamos la ruta del backen para cerrar la sesion alli tambien
    this.http.post(`${this.baseUrl}/logout`, {}).subscribe({
      next: () => {
        //Limpiamos el token y el usuario ( esto es igual que lo tenias )
        localStorage.removeItem('token');
        localStorage.removeItem('usuario');
        // Redirigimos a la pagina de login o el landing mejor?¿
        this.router.navigate(['']);
      },
      error: (err) => {
        console.error('Error al cerrar sesión en el backend:', err);
        // Aún así redirige al login aunque no se cierre correctamente en el backend
        localStorage.removeItem('token');
        localStorage.removeItem('usuario');
        this.router.navigate(['']);
      }
    });
  }

  obtenerRol(): string {
    const usuario = this.obtenerUsuario();
    return usuario?.rol || '';
  }
}
