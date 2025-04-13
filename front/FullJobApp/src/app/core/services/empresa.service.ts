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

  getEmpresa(id: number): Observable<IEmpresa> {
    return this.http.get<IEmpresa>(`${this.baseUrl}/${id}`);
  }

  
  buscarPorNombre(nombre: string): Observable<IEmpresa[]> {
    return this.http.get<IEmpresa[]>(`${this.baseUrl}/buscar/${nombre}`);
  }

  actualizarEmpresa(id: number, dto: IEmpresa): Observable<IEmpresa> {
    return this.http.put<IEmpresa>(`${this.baseUrl}/${id}`, dto);
  }

  eliminarEmpresa(id: number): Observable<IEmpresa> {
    return this.http.delete<IEmpresa>(`${this.baseUrl}/${id}`);
  }

  //  // ⚠️ Este método no funcionará sin POST en backend
  //  crearEmpresa(dto: IEmpresa): Observable<IEmpresa> {
  //   return this.http.post<IEmpresa>(this.baseUrl, dto);
  // }

}

