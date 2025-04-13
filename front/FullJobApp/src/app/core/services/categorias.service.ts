import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ICategoria } from '../interfaces/Icategoria';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CategoriasService {
  private baseUrl = 'http://localhost:9007/categorias';

  constructor(private http: HttpClient) {}

  getCategorias(): Observable<ICategoria[]> {
    return this.http.get<ICategoria[]>(this.baseUrl);
  }

  eliminarCategoria(id: number): Observable<ICategoria> {
      return this.http.delete<ICategoria>(`${this.baseUrl}/${id}`);
    }

    createCategoria(categoria: ICategoria) {
      return this.http.post<ICategoria>('http://localhost:9007/categorias', categoria);
    }
}
