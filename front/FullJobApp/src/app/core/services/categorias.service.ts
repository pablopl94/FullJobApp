import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICategoria } from '../interfaces/ICategoria';

@Injectable({
  providedIn: 'root',
})
export class CategoriasService {

  // URL del backend
  private apiUrl = 'http://localhost:9007/categorias';

  constructor(private http: HttpClient) {}

  // Obtener todas las categorías
  getCategorias(): Observable<ICategoria[]> {
    return this.http.get<ICategoria[]>(this.apiUrl);
  }

  // Obtener una categoría por su ID
  getCategoriaPorId(id: number): Observable<ICategoria> {
    return this.http.get<ICategoria>(`${this.apiUrl}/${id}`);
  }

  // Crear una nueva categoría
  crearCategoria(categoria: ICategoria): Observable<ICategoria> {
    return this.http.post<ICategoria>(this.apiUrl, categoria);
  }

  // Actualizar una categoría existente
  actualizarCategoria(id: number, categoria: ICategoria): Observable<ICategoria> {
    return this.http.put<ICategoria>(`${this.apiUrl}/${id}`, categoria);
  }

  // Eliminar una categoría por ID (la respuesta es un mensaje)
  eliminarCategoria(id: number): Observable<string> {
  return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
}

}