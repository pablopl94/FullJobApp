import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { IEmpresa } from '../interfaces/IEmpresa';
import { Observable } from 'rxjs';
import { IUsuario } from '../interfaces/iusuario';
import { UsuarioService } from './usuario.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:9007/auth';

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
    this.http.post(`${this.baseUrl}/logout`, {}).subscribe({
      next: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('usuario');

        this.router.navigate(['']);
      },
      error: (err) => {
        console.error('Error al cerrar sesión en el backend:', err);

        localStorage.removeItem('token');
        localStorage.removeItem('usuario');
        this.router.navigate(['']);
      },
    });
  }

  obtenerRol(): string {
    const usuario = this.obtenerUsuario();
    return usuario?.rol || '';
  }

  registrarCliente(data: {
    nombre: string;
    apellidos: string;
    email: string;
    password: string;
  }) {
    return this.http.post<any>('http://localhost:9007/auth/alta/cliente', data);
  }

  registrarAdmin(admin: IUsuario) {
    return this.http.post<IUsuario>(
      `${this.baseUrl}/alta/administrador`,
      admin
    );
  }
}
