import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICategoria } from '../interfaces/Icategoria';



@Injectable({
  providedIn: 'root',
})
export class CategoriasService {
  private apiUrl = 'http://localhost:9007/categorias';

  constructor(private http: HttpClient) {}

  getCategorias(): Observable<ICategoria[]> {
    return this.http.get<ICategoria[]>(this.apiUrl);
  }

  getCategoriaPorId(id: number): Observable<ICategoria> {
    return this.http.get<ICategoria>(`${this.apiUrl}/${id}`);
  }

  crearCategoria(categoria: ICategoria): Observable<ICategoria> {
    return this.http.post<ICategoria>(this.apiUrl, categoria);
  }

  actualizarCategoria(id: number, categoria: ICategoria): Observable<ICategoria> {
    return this.http.put<ICategoria>(`${this.apiUrl}/${id}`, categoria);
  }

  eliminarCategoria(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getAll(): Observable<ICategoria[]> {
    return this.http.get<ICategoria[]>(`${this.apiUrl}/categorias`);
  }
}
