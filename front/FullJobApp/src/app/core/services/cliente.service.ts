import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private baseUrl = 'http://localhost:9007/clientes/';

  constructor(private http: HttpClient, private router: Router) {}

  // //Metodo para obtener todos los usuarios
  // obtenerClientes(): Observable <any[]> {
  //   return this.http.get<IUsuario[]>(`${this.baseUrl}`);
  // }



}
