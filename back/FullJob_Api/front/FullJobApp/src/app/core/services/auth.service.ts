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
    localStorage.removeItem('token');
    localStorage.removeItem('usuario');
    this.router.navigate(['/auth/login']);
  }

  obtenerRol(): string {
    const usuario = this.obtenerUsuario();
    return usuario?.rol || '';
  }
}
