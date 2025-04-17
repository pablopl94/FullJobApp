import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { IEmpresa } from '../interfaces/IEmpresa';

import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EmpresaService {
  private baseUrl = 'http://localhost:9007/empresas';

  // Sujeto reactivo de empresas
  private empresasSubject = new BehaviorSubject<IEmpresa[]>([]);
  public empresas$ = this.empresasSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  // Llama al backend y actualiza el BehaviorSubject
  fetchEmpresas(): void {
    this.http.get<IEmpresa[]>(this.baseUrl).subscribe({
      next: (empresas) => this.empresasSubject.next(empresas),
      error: (err) => console.error('Error al cargar empresas', err),
    });
  }

  // Buscar sin afectar al estado global
  buscarPorNombre(nombre: string): Observable<IEmpresa[]> {
    return this.http.get<IEmpresa[]>(`${this.baseUrl}/buscar/${nombre}`);
  }

  actualizarEmpresa(empresa: IEmpresa): Observable<IEmpresa> {
    return this.http
      .put<IEmpresa>(`${this.baseUrl}/${empresa.idEmpresa}`, empresa)
      .pipe(tap(() => this.fetchEmpresas()));
  }

  eliminarEmpresa(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      tap(() => {
        const listaActual = this.empresasSubject.getValue();
        const nuevaLista = listaActual.filter((e) => e.idEmpresa !== id);
        this.empresasSubject.next(nuevaLista);
      })
    );
  }

  getDetallesEmpresaAutenticada(): Observable<IEmpresa> {
    return this.http.get<IEmpresa>(`${this.baseUrl}/perfil`);
  }

  getEmpresaById(id: number): Observable<IEmpresa> {
    return this.http.get<IEmpresa>(`${this.baseUrl}/${id}`);
  }

  crearEmpresa(data: IEmpresa): Observable<IEmpresa> {
    return this.http
      .post<IEmpresa>(`http://localhost:9007/auth/alta/empresa`, data)
      .pipe(tap(() => this.fetchEmpresas()));
  }
}
