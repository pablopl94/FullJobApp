import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ISolicitud } from '../interfaces/isolicitud';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SolicitudesService {

  private baseUrl = 'http://localhost:9007/solicitudes/';

  constructor(private http: HttpClient, private router: Router) {}

  //Metodo para obtener todas las solicitudes de un usuario 
  //En el back ya tiene la logica que depende de si es un CLIENTE O UNA EMPRESA
  //te enseñara las solicitudes enviadas o recibidas
  obtenerMisSolicitudes(): Observable<ISolicitud[]> {
    return this.http.get<ISolicitud[]>(`${this.baseUrl}missolicitudes`);
  }

  //Obtener las 5 ultimas solicitudes de la base de datos para el dashboard
   obtenerUltimasSolicitudes(): Observable<ISolicitud[]> {
     return this.http.get<ISolicitud[]>(`${this.baseUrl}top5`);
    }

  //Metodo para que el usuario con rol CLIENTE cancele una solicitud ya enviada
  cancelarSolicitud(id: number): Observable<ISolicitud> {
    return this.http.put<ISolicitud>(`${this.baseUrl}cancelar/${id}`, {});
  }

  //Metodo para obtener todas las solicitudes de una vacante *DE MOMENTO NO HACE FALTA*
  obtenerSolicitudesPorVacante(idVacante: number): Observable<ISolicitud[]> {
    return this.http.get<ISolicitud[]>(`${this.baseUrl}vacante/${idVacante}`);
  }

  //Metodo para obtener que la EMPRESA pueda ver detalles de una solicitud de un CLIENTE
  obtenerDetalleSolicitud(id: number): Observable<ISolicitud> {
    return this.http.get<ISolicitud>(`${this.baseUrl}${id}`);
  }

  //Metodo para asignar a una solicitud a un CLIENTE por parte de la EMPRESA
  asignarVacante(id: number): Observable<string> {
    return this.http.put(`${this.baseUrl}asignar/${id}`, {}, { responseType: 'text' });
  }


}