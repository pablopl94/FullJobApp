import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of, tap } from 'rxjs';
import { IVacante } from '../interfaces/IVacante';


@Injectable({
  providedIn: 'root'
})
export class VacantesService {

  // API para Empresa
  private apiUrl = 'http://localhost:9007/vacantes';

  private vacantesSubject = new BehaviorSubject<IVacante[]>([]);
  vacantes$ = this.vacantesSubject.asObservable();

  constructor(private http: HttpClient) {}


  getMisVacantes(): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/misvacantes`);
  }

  //Aqui le decimos que getVacantes este sujeto a los cambios de los demas metodos en lo que lo definamos
  cargarVacantes(): void {
    this.getMisVacantes().subscribe((vacantes) => {
      this.vacantesSubject.next(vacantes);
    });
  }

  //Metodo para que una empresa pueda cancelar una vacante
  cancelarVacante(id: number) {
    return this.http.delete(`${this.apiUrl}/cancelar/${id}`, { responseType: 'text' }).pipe(
      tap(() => this.cargarVacantes())
    );
  }

  //Metodo para actualizr vacante con un usuario Empresa
  actualizarVacante(vacante:IVacante): Observable<IVacante> {
    return this.http.put<IVacante>(`${this.apiUrl}/editar/${vacante.idVacante}`, vacante);
  }

  //Metodo para publicar una vacante con un usuario Empresa
  publicarVacante(vacante:IVacante): Observable<IVacante> {
    return this.http.post<IVacante>(`${this.apiUrl}/publicar`, vacante);
  }
  
  //Metodo para buscar una vacante por su id
  findById(id:number): Observable<IVacante>{
    return this.http.get<IVacante>(`${this.apiUrl}/${id}`)
  }

  //Metodo para obtener los tipos de contrato "detalles" de una vacante
  getTiposContrato():Observable<string[]>{
    return this.http.get<string[]>(`${this.apiUrl}/tiposcontrato`)
  }


  private vacantes: IVacante[] = [
    {
      idVacante: 1,
      nombre: 'Desarrollador Backend',
      descripcion: 'Se busca programador con experiencia en Java y Spring.',
      detalles: 'INDEFINIDO',
      fecha: '2025-04-01',
      salario: 32000,
      estatus: 'CREADA',
      destacado: 0,
      imagen: '',
      nombreCategoria: 'Desarrollo',
      nombreEmpresa: 'Tech Solutions',
      idCategoria: 1,
    },
    {
      idVacante: 2,
      nombre: 'Analista de Datos',
      descripcion: 'Experto en SQL, Python y herramientas de BI.',
      detalles: 'TEMPORAL',
      fecha: '2025-04-02',
      salario: 30000,
      estatus: 'CREADA',
      destacado: 0,
      imagen: '',
      nombreCategoria: 'Desarrollo',
      nombreEmpresa: 'Tech Solutions',
      idCategoria: 1,
    },
    {
      idVacante: 3,
      nombre: 'Arquitecto de Software',
      descripcion: 'Liderar la arquitectura de una plataforma SaaS.',
      detalles: 'INDEFINIDO',
      fecha: '2025-04-03',
      salario: 40000,
      estatus: 'CREADA',
      destacado: 0,
      imagen: '',
      nombreCategoria: 'Desarrollo',
      nombreEmpresa: 'Tech Solutions',
      idCategoria: 1,
    },
    {
      idVacante: 4,
      nombre: 'DevOps Engineer',
      descripcion: 'Automatización de pipelines y mantenimiento de infraestructuras en AWS.',
      detalles: 'AUTONOMO',
      fecha: '2025-04-04',
      salario: 35000,
      estatus: 'CREADA',
      destacado: 0,
      imagen: '',
      nombreCategoria: 'Desarrollo',
      nombreEmpresa: 'Tech Solutions',
      idCategoria: 1,
    },
    {
      idVacante: 5,
      nombre: 'Diseñador Gráfico',
      descripcion: 'Diseño de material publicitario, branding y material digital.',
      detalles: 'PRACTICAS',
      fecha: '2025-04-05',
      salario: 28000,
      estatus: 'CREADA',
      destacado: 0,
      imagen: '',
      nombreCategoria: 'Desarrollo',
      nombreEmpresa: 'Tech Solutions',
      idCategoria: 1,
    }
  ];

  getVacantes(): Observable<IVacante[]> {
    return of(this.vacantes);
  }

}
