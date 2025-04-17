import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUsuario } from '../interfaces/iusuario';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private apiUrl = 'http://localhost:9007/clientes';

  constructor(private http: HttpClient) {}

  getCandidatos(): Observable<IUsuario[]> {
    return this.http.get<IUsuario[]>(this.apiUrl);
  }

  activar(email: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/activar/${email}`, {});
  }

  desactivar(email: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/desactivar/${email}`, {});
  }

  findAll() {
    return this.http.get<IUsuario[]>(`${this.apiUrl}/clientes`);
  }

  suspenderUsuario(email: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/desactivar/${email}`, {});
  }

  crearUsuario(usuario: IUsuario) {
    return this.http.post<IUsuario>(`${this.apiUrl}/cliente`, usuario); // ClienteController
  }

  actualizarAdmin(id: string, admin: IUsuario) {
    return this.http.put<IUsuario>(`${this.apiUrl}/modificar/${id}`, admin);
  }

  obtenerUsuario(email: string): Observable<IUsuario> {
    return this.http.get<IUsuario>(`${this.apiUrl}/${email}`);
  }

  obtenerUsuarioPorId(id: string) {
    return this.http.get<IUsuario>(`${this.apiUrl}/cliente/${id}`);
  }

  eliminarUsuario(email: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/cliente/${email}`);
  }

  obtenerUsuarioPorEmail(email: string): Observable<IUsuario> {
    return this.http.get<IUsuario>(`${this.apiUrl}/${email}`);
  }

  actualizarUsuarioPorEmail(
    email: string,
    usuario: IUsuario
  ): Observable<IUsuario> {
    return this.http.put<IUsuario>(
      `${this.apiUrl}/modificar/${email}`,
      usuario
    );
  }
}
