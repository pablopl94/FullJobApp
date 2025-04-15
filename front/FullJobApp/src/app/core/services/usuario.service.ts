import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUsuario } from '../interfaces/iusuario';

@Injectable({
  providedIn: 'root'
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
  
  suspenderUsuario(email: string) {
    return this.http.put(`${this.apiUrl}/clientes/desactivar/${email}`, {});
  }
 
}
