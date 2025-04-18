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

  // Sujeto para vacantes creadas
  private vacantesSubject = new BehaviorSubject<IVacante[]>([]);
  vacantes$ = this.vacantesSubject.asObservable();

  // Sujeto para vacantes asignadas
  private vacantesAsignadasSubject = new BehaviorSubject<IVacante[]>([]);
  vacantesAsignadas$ = this.vacantesAsignadasSubject.asObservable();

  // Sujeto para vacantes canceladas
  private vacantesCanceladasSubject = new BehaviorSubject<IVacante[]>([]);
  vacantesCanceladas$ = this.vacantesCanceladasSubject.asObservable();

  constructor(private http: HttpClient) {}

  //Metodo para cargar tabla de las vacantes que esta en estado creado es decir sin asignar, ni cancelada...
  getMisVacantesCreadas(): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/misvacantes/creadas`);
  }
  
  //Metodo para cargar tabla de las vacantes que se han asginado
  getMisVacantesAsignadas(): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/misvacantes/asignadas`);
  }

  //Metodo para cargar tabla de las vacantes que se han cancelado
  getMisVacantesCanceladas(): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/misvacantes/canceladas`);
  }

  //Aqui le decimos que getVacantes este sujeto a los cambios de los demas metodos en lo que lo definamos
  cargarVacantes(): void {
    this.getMisVacantesCreadas().subscribe((vacantes) => {
      this.vacantesSubject.next(vacantes);
    });
  }

  // Metodo para cargar las vacantes asignadas y notificar cambios
  cargarVacantesAsignadas(): void {
    this.getMisVacantesAsignadas().subscribe((vacantes) => {
      this.vacantesAsignadasSubject.next(vacantes);
    });
  }

  // Metodo para cargar las vacantes canceladas y notificar cambios
  cargarVacantesCanceladas(): void {
    this.getMisVacantesCanceladas().subscribe((vacantes) => {
      this.vacantesCanceladasSubject.next(vacantes);
    });
  }

  //Metodo para que una empresa pueda cancelar una vacante
  cancelarVacante(id: number) {
    return this.http.delete(`${this.apiUrl}/cancelar/${id}`, { responseType: 'text' }).pipe(
      tap(() => {
        this.cargarVacantes();
        this.cargarVacantesCanceladas();
        this.cargarVacantesAsignadas();
      })
    );
  }

  //Metodo para actualizr vacante con un usuario Empresa
  actualizarVacante(vacante: IVacante): Observable<IVacante> {
    return this.http.put<IVacante>(`${this.apiUrl}/editar/${vacante.idVacante}`, vacante);
  }

  //Metodo para publicar una vacante con un usuario Empresa
  publicarVacante(vacante: IVacante): Observable<IVacante> {
    return this.http.post<IVacante>(`${this.apiUrl}/publicar`, vacante);
  }
  
  //Metodo para buscar una vacante por su id
  findById(id: number): Observable<IVacante> {
    return this.http.get<IVacante>(`${this.apiUrl}/${id}`);
  }

  //Metodo para obtener los tipos de contrato "detalles" de una vacante
  getTiposContrato(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/tiposcontrato`);
  }

  // Datos de prueba para testeo local
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
    }
    // ...etc
  ];

  getVacantes(): Observable<IVacante[]> {
    return of(this.vacantes);
  }

  getVacantesCandidato(): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}`);
  }
  postularme(idVacante: number, solicitudDto: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/inscribirse/${idVacante}`, solicitudDto);
  }

  getVacantesPorEmpresa(nombreEmpresa: string): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/filtrar/empresa/${encodeURIComponent(nombreEmpresa)}`);
  }
  getVacantesPorCategoria(idCategoria: number): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/filtrar/categoria/${idCategoria}`);
  }
  getVacantesPorContrato(tipoContrato: string): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/filtrar/contrato/${tipoContrato}`);
  }
}