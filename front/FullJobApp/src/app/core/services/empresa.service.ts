import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { IEmpresa } from '../interfaces/IEmpresa';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class EmpresaService {

  private baseUrl = 'http://localhost:9007/empresas';

  constructor(private http: HttpClient, private router: Router) {}

  getEmpresas(): Observable<IEmpresa[]> {
    return this.http.get<IEmpresa[]>(this.baseUrl);    
  }
 
  crearEmpresa(empresa: IEmpresa): Observable<IEmpresa> {
    return this.http.post<IEmpresa>(this.baseUrl, empresa);
  }
  
  buscarPorNombre(nombre: string): Observable<IEmpresa[]> {
    return this.http.get<IEmpresa[]>(`${this.baseUrl}/buscar/${nombre}`);
  }

  actualizarEmpresa(empresa: IEmpresa): Observable<IEmpresa> {
    return this.http.put<IEmpresa>(`${this.baseUrl}/${empresa.idEmpresa}`, empresa);
  }

  eliminarEmpresa(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
  
  getDetallesEmpresaAutenticada():Observable<IEmpresa> {
    return this.http.get<IEmpresa>(`${this.baseUrl}/perfil`);
  }

  getEmpresaById(id: number): Observable<IEmpresa> {
    return this.http.get<IEmpresa>(`${this.baseUrl}/${id}`);
  }

}

