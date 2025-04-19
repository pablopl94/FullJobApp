import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { IEmpresa } from '../interfaces/IEmpresa';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class EmpresaService {
  private baseUrl =  `${environment.apiUrl}/empresas`;
  private authUrl =  `${environment.apiUrl}/auth`;

  // Sujeto reactivo para almacenar y emitir las empresas
  private empresasSubject = new BehaviorSubject<IEmpresa[]>([]);
  public empresas$ = this.empresasSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  // Carga todas las empresas y las guarda en el subject
  fetchEmpresas(): void {
    this.http.get<IEmpresa[]>(this.baseUrl).subscribe({
      next: (empresas) => this.empresasSubject.next(empresas),
      error: (err) => console.error('Error al cargar empresas', err),
    });
  }

  // Devuelve las empresas directamente sin afectar al subject
  getEmpresas(): Observable<IEmpresa[]> {
    return this.http.get<IEmpresa[]>(this.baseUrl);
  }

  // Buscar empresas por nombre (sin afectar al estado global)
  buscarPorNombre(nombre: string): Observable<IEmpresa[]> {
    return this.http.get<IEmpresa[]>(`${this.baseUrl}/buscar/${nombre}`);
  }

  // Actualiza los datos de una empresa y refresca la lista
  actualizarEmpresa(empresa: IEmpresa): Observable<IEmpresa> {
    return this.http.put<IEmpresa>(`${this.baseUrl}/${empresa.idEmpresa}`, empresa).pipe(
      tap(() => this.fetchEmpresas())
    );
  }

  // Elimina una empresa del backend y del estado local
  eliminarEmpresa(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      tap(() => {
        const listaActual = this.empresasSubject.getValue();
        const nuevaLista = listaActual.filter(e => e.idEmpresa !== id);
        this.empresasSubject.next(nuevaLista);
      })
    );
  }

  // Devuelve los detalles de la empresa autenticada
  getDetallesEmpresaAutenticada(): Observable<IEmpresa> {
    return this.http.get<IEmpresa>(`${this.baseUrl}/perfil`);
  }

  // Obtiene empresa por ID
  getEmpresaById(id: number): Observable<IEmpresa> {
    return this.http.get<IEmpresa>(`${this.baseUrl}/${id}`);
  }

  // Registra una nueva empresa y actualiza la lista global
  crearEmpresa(data: IEmpresa): Observable<IEmpresa> {
    return this.http
      .post<IEmpresa>(`${this.authUrl}/auth/alta/empresa`, data)
      .pipe(tap(() => this.fetchEmpresas()));
  }
}
