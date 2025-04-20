import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { IVacante } from '../interfaces/IVacante';
import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class VacantesService {

  private apiUrl = `${environment.apiUrl}/vacantes`;

  // Lista de vacantes creadas por la empresa
  private vacantesSubject = new BehaviorSubject<IVacante[]>([]);
  vacantes$ = this.vacantesSubject.asObservable();

  // Lista de vacantes asignadas
  private vacantesAsignadasSubject = new BehaviorSubject<IVacante[]>([]);
  vacantesAsignadas$ = this.vacantesAsignadasSubject.asObservable();

  // Lista de vacantes canceladas
  private vacantesCanceladasSubject = new BehaviorSubject<IVacante[]>([]);
  vacantesCanceladas$ = this.vacantesCanceladasSubject.asObservable();

  // Lista de todas las vacantes activas visibles para cualquier usuario
  private vacantespublicasSubject = new BehaviorSubject<IVacante[]>([]);
  vacantesPublicas$ = this.vacantespublicasSubject.asObservable();

  constructor(private http: HttpClient) {}

  // Carga las vacantes creadas y las guarda en el observable
  cargarMisVacantes(): void {
    this.getMisVacantesCreadas().subscribe((vacantes) => {
      this.vacantesSubject.next(vacantes);
    });
  }

  // Carga todas vacantes activas píblicas
  cargarTodasLasVacantesActivas(): void {
    this.getTodasLasVacantes().subscribe((vacantes) => {
      this.vacantespublicasSubject.next(vacantes);
    });
  }

  // Carga las vacantes asignadas y las guarda en el observable
  cargarVacantesAsignadas(): void {
    this.getMisVacantesAsignadas().subscribe((vacantes) => {
      this.vacantesAsignadasSubject.next(vacantes);
    });
  }

  // Carga las vacantes canceladas y las guarda en el observable
  cargarVacantesCanceladas(): void {
    this.getMisVacantesCanceladas().subscribe((vacantes) => {
      this.vacantesCanceladasSubject.next(vacantes);
    });
  }
  
  // Trae las vacantes en estado "CREADA" de la empresa actual
  getMisVacantesCreadas(): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/misvacantes/creadas`);
  }

  // Trae las vacantes asignadas de la empresa actual
  getMisVacantesAsignadas(): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/misvacantes/asignadas`);
  }

  // Trae las vacantes canceladas de la empresa actual
  getMisVacantesCanceladas(): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/misvacantes/canceladas`);
  }

  // Trae todas las vacantes activas públicas
   getTodasLasVacantes(): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}`);
  }

  // Cancela una vacante por su ID y actualiza todas las listas
  cancelarVacante(id: number) {
    return this.http.delete(`${this.apiUrl}/cancelar/${id}`, { responseType: 'text' }).pipe(
      tap(() => {
        this.cargarMisVacantes();
        this.cargarVacantesCanceladas();
        this.cargarVacantesAsignadas();
        this.cargarTodasLasVacantesActivas();
      })
    );
  }

  // Actualiza los datos de una vacante
  actualizarVacante(vacante: IVacante): Observable<IVacante> {
    return this.http.put<IVacante>(`${this.apiUrl}/editar/${vacante.idVacante}`, vacante);
  }

  // Publica una nueva vacante
  publicarVacante(vacante: IVacante): Observable<IVacante> {
    return this.http.post<IVacante>(`${this.apiUrl}/publicar`, vacante);
  }

  // Busca una vacante por su ID
  findById(id: number): Observable<IVacante> {
    return this.http.get<IVacante>(`${this.apiUrl}/${id}`);
  }

  // Devuelve los tipos de contrato disponibles
  getTiposContrato(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/tiposcontrato`);
  }

  getVacantesCandidato(): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}`);
  }

  postularme(idVacante: number, formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/inscribirse/${idVacante}`, formData);
  }
  getVacantesPorEmpresa(nombreEmpresa: string): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/filtrar/empresa/${(nombreEmpresa)}`);
  }
  getVacantesPorCategoria(idCategoria: number): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/filtrar/categoria/${idCategoria}`);
  }
  getVacantesPorContrato(tipoContrato: string): Observable<IVacante[]> {
    return this.http.get<IVacante[]>(`${this.apiUrl}/filtrar/contrato/${tipoContrato}`);
  }
}