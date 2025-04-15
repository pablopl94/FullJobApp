import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICategoria } from '../interfaces/ICategoria';


@Injectable({
  providedIn: 'root',
})
export class CategoriasService {
  private baseUrl = 'http://localhost:9007/categorias';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ICategoria[]> {
    return this.http.get<ICategoria[]>(this.baseUrl);
  }

  getById(id: number): Observable<ICategoria> {
    return this.http.get<ICategoria>(`${this.baseUrl}/${id}`);
  }

  create(categoria: ICategoria): Observable<ICategoria> {
    return this.http.post<ICategoria>(this.baseUrl, categoria);
  }

  update(id: number, categoria: ICategoria): Observable<ICategoria> {
    return this.http.put<ICategoria>(`${this.baseUrl}/${id}`, categoria);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
