import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { IUsuario } from '../interfaces/iusuario';
import { IAdminRegistro } from '../interfaces/IAdminRegistro';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {

  private apiUrl = `${environment.apiUrl}/clientes`;

  // BehaviorSubject para almacenar y compartir los candidatos
  private candidatosSubject = new BehaviorSubject<IUsuario[]>([]);

  // Observable para suscribirse a los candidatos desde otros componentes
  public candidatos$ = this.candidatosSubject.asObservable();

  constructor(private http: HttpClient) {}

  // Carga los candidatos desde el backend y actualiza el BehaviorSubject
  getCandidatos(): void {
    this.http.get<IUsuario[]>(this.apiUrl)
      .pipe(tap(candidatos => this.candidatosSubject.next(candidatos)))
      .subscribe();
  }

  // Registra un nuevo administrador en el backend
  registrarAdmin(admin: IAdminRegistro): Observable<IAdminRegistro> {
    return this.http.post<IAdminRegistro>(`${this.apiUrl}/alta/admin`,admin );
  }
  
  // Refresca manualmente los candidatos
  refreshCandidatos(): void {
    this.getCandidatos();
  }

  // Activa un usuario por email
  activar(email: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/activar/${email}`, {}).pipe(
      tap(() => {
        this.getCandidatos();
      })
    );
  }

  // Desactiva un usuario por email
  desactivar(email: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/desactivar/${email}`, {}).pipe(
      tap(() => {
        this.getCandidatos();
      })
    );
  }
  // Obtiene todos los clientes
  findAll(): Observable<IUsuario[]> {
    return this.http.get<IUsuario[]>(`${this.apiUrl}/clientes`);
  }

  // Suspende un usuario (igual que desactivar)
  suspenderUsuario(email: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/desactivar/${email}`, {});
  }

  // Crea un nuevo usuario
  crearUsuario(usuario: IUsuario): Observable<IUsuario> {
    return this.http.post<IUsuario>(`${this.apiUrl}/cliente`, usuario);
  }

  // Actualiza un usuario admin por ID
  actualizarAdmin(email: string, admin: IAdminRegistro): Observable<IAdminRegistro> {
    return this.http.put<IAdminRegistro>(`${this.apiUrl}/modificar/${email}`, admin);
  }

  // Obtiene un usuario por email
  obtenerUsuario(email: string): Observable<IUsuario> {
    return this.http.get<IUsuario>(`${this.apiUrl}/${email}`);
  }

  // Obtiene un usuario por ID
  obtenerUsuarioPorId(id: string): Observable<IUsuario> {
    return this.http.get<IUsuario>(`${this.apiUrl}/cliente/${id}`);
  }

  // Elimina un usuario por email
  eliminarUsuario(email: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/cliente/${email}`);
  }

  // También obtiene un usuario por email (duplicado)
  obtenerUsuarioPorEmail(email: string): Observable<IUsuario> {
    return this.http.get<IUsuario>(`${this.apiUrl}/${email}`);
  }

  // Actualiza un usuario por email
  actualizarUsuarioPorEmail(email: string, usuario: IUsuario): Observable<IUsuario> {
    return this.http.put<IUsuario>(`${this.apiUrl}/modificar/${email}`, usuario);
  }

  
}