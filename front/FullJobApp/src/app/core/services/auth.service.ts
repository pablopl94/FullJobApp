import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { IClienteRegistro } from '../interfaces/IClienteRegistro';
import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  // URL base del backend para auth
  private baseUrl = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient, private router: Router) {}

  // Iniciar sesión con email y contraseña
  login(email: string, password: string) {
    console.log('API URL en uso:', environment.apiUrl);
    return this.http.post(`${this.baseUrl}/login`, { email, password });
    
  }

  // Guarda el token y los datos del usuario en localStorage
  guardarUsuarioYToken(token: string, usuario: any) {
    localStorage.setItem('token', token);
    localStorage.setItem('usuario', JSON.stringify(usuario));
  }

  // Registra un nuevo cliente en el backend
  registrarCliente(cliente: IClienteRegistro): Observable<any> {
    return this.http.post(`${this.baseUrl}/alta/cliente`, cliente);
  }

  // Obtiene el token guardado
  obtenerToken(): string | null {
    return localStorage.getItem('token');
  }

  // Devuelve el usuario guardado en localStorage (o null)
  obtenerUsuario(): any {
    const user = localStorage.getItem('usuario');
    return user ? JSON.parse(user) : null;
  }

  // Comprueba si el usuario está logueado
  estaLogueado(): boolean {
    return !!this.obtenerToken();
  }

  // Cierra sesión: llama al backend y limpia el localStorage
  logout() {
    this.http.post(`${this.baseUrl}/logout`, {}).subscribe({
      next: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('usuario');
        this.router.navigate(['']); // redirige al inicio
      },
      error: (err) => {
        console.error('Error al cerrar sesión en el backend:', err);
        localStorage.removeItem('token');
        localStorage.removeItem('usuario');
        this.router.navigate(['']);
      },
    });
  }
 
  // Devuelve el rol del usuario logueado (si lo hay)
  obtenerRol(): string {
    const usuario = this.obtenerUsuario();
    return usuario?.rol || '';
  }

}