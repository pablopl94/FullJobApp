import { Injectable } from '@angular/core';
import { Vacante } from '../interfaces/vacante';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VacantesService {

  //private apiUrl = 'http://localhost:9007/vacantes';

  constructor(private http: HttpClient) {}

  // getVacantes(): Observable<Vacante[]> {
  //   return this.http.get<Vacante[]>(this.apiUrl);
  // }

  private vacantes: Vacante[] = [
    {
      id_vacante: 1,
      nombre: 'Desarrollador Backend',
      descripcion: 'Se busca programador con experiencia en Java y Spring.',
      detalles: 'INDEFINIDO',
      fecha: '2025-04-01',
      salario: 32000,
      estatus: 'CREADA',
      destacado: true,
      imagen: '',
      id_Categoria: 1,
      id_empresa: 1
    },
    {
      id_vacante: 2,
      nombre: 'Analista de Datos',
      descripcion: 'Experto en SQL, Python y herramientas de BI.',
      detalles: 'TEMPORAL',
      fecha: '2025-04-02',
      salario: 30000,
      estatus: 'CREADA',
      destacado: true,
      imagen: '',
      id_Categoria: 2,
      id_empresa: 2
    },
    {
      id_vacante: 3,
      nombre: 'Arquitecto de Software',
      descripcion: 'Liderar la arquitectura de una plataforma SaaS.',
      detalles: 'INDEFINIDO',
      fecha: '2025-04-03',
      salario: 40000,
      estatus: 'CREADA',
      destacado: false,
      imagen: '',
      id_Categoria: 1,
      id_empresa: 3
    },
    {
      id_vacante: 4,
      nombre: 'DevOps Engineer',
      descripcion: 'Automatización de pipelines y mantenimiento de infraestructuras en AWS.',
      detalles: 'AUTONOMO',
      fecha: '2025-04-04',
      salario: 35000,
      estatus: 'CREADA',
      destacado: false,
      imagen: '',
      id_Categoria: 1,
      id_empresa: 4
    },
    {
      id_vacante: 5,
      nombre: 'Diseñador Gráfico',
      descripcion: 'Diseño de material publicitario, branding y material digital.',
      detalles: 'PRACTICAS',
      fecha: '2025-04-05',
      salario: 28000,
      estatus: 'CREADA',
      destacado: true,
      imagen: '',
      id_Categoria: 2,
      id_empresa: 5
    }
  ];

  getVacantes(): Observable<Vacante[]> {
    return of(this.vacantes);
  }

}
