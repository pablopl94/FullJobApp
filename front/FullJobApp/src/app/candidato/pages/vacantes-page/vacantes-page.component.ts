import { Component, ElementRef, inject, ViewChild } from '@angular/core';
import { IVacante } from '../../../core/interfaces/IVacante';
import { VacantesService } from '../../../core/services/vacantes.service';
import { Router } from '@angular/router';
import { VacanteCardComponent } from '../../components/vacante-card/vacante-card.component';
import { FormsModule } from '@angular/forms';
import { CategoriasService } from '../../../core/services/categorias.service';
import { ICategoria } from '../../../core/interfaces/ICategoria';
import { IEmpresa } from '../../../core/interfaces/IEmpresa';
import { EmpresaService } from '../../../core/services/empresa.service';

@Component({
  selector: 'app-vacantes-page',
  imports: [VacanteCardComponent, FormsModule],
  templateUrl: './vacantes-page.component.html',
  styleUrl: './vacantes-page.component.css'
})
export class VacantesPageComponent {
   // Lista de vacantes visibles
   vacantes: IVacante[] = [];

   // Listas auxiliares para filtros
   categorias: ICategoria[] = [];
   empresas: IEmpresa[] = [];
   contratos: string[] = [];
 
   // Filtros seleccionados
   filtroCategoria: number | '' = '';
   filtroEmpresa: string = '';
   filtroContrato: string = '';
 
   // Inyección de servicios
   private vacanteService = inject(VacantesService);
   private categoriaService = inject(CategoriasService);
   private empresaService = inject(EmpresaService);
   private router = inject(Router);
 
   ngOnInit(): void {
     // Cargar todas las vacantes públicas
     this.cargarVacantes();
 
     // Cargar todas las categorías
     this.categoriaService.getCategorias().subscribe({
       next: (data) => this.categorias = data,
       error: (err) => console.error('Error al cargar categorías', err)
     });
 
     // Cargar empresas en el BehaviorSubject
     this.empresaService.fetchEmpresas();
 
     // Escuchar los cambios en la lista de empresas
     this.empresaService.empresas$.subscribe({
       next: (data) => this.empresas = data,
       error: (err) => console.error('Error al cargar empresas', err)
     });
   }
 
   // Carga todas las vacantes públicas
   cargarVacantes(): void {
     this.vacanteService.getVacantesCandidato().subscribe({
       next: (data) => {
         this.vacantes = data;
         this.contratos = [...new Set(data.map(v => v.detalles))]; // Contratos únicos
       },
       error: (err) => console.error('Error al cargar vacantes', err)
     });
   }
 
   // Aplica filtros por separado (por ahora no combinados)
   aplicarFiltros(): void {
     if (this.filtroCategoria !== '') {
       this.vacanteService.getVacantesPorCategoria(Number(this.filtroCategoria)).subscribe({
         next: (data) => this.vacantes = data,
         error: (err) => console.error('Error al filtrar por categoría', err)
       });
       return;
     }
 
     if (this.filtroEmpresa !== '') {
       this.vacanteService.getVacantesPorEmpresa(this.filtroEmpresa).subscribe({
         next: (data) => this.vacantes = data,
         error: (err) => console.error('Error al filtrar por empresa', err)
       });
       return;
     }
 
     if (this.filtroContrato !== '') {
       this.vacanteService.getVacantesPorContrato(this.filtroContrato).subscribe({
         next: (data) => this.vacantes = data,
         error: (err) => console.error('Error al filtrar por contrato', err)
       });
       return;
     }
 
     // Si no hay filtros activos, recarga todo
     this.cargarVacantes();
   }
 
   // Eventos de cambio de filtros
   onCategoriaChange(event: Event): void {
     const value = (event.target as HTMLSelectElement).value;
     this.filtroCategoria = value === '' ? '' : Number(value);
     this.aplicarFiltros();
   }
 
   onEmpresaChange(event: Event): void {
     const value = (event.target as HTMLSelectElement).value;
     this.filtroEmpresa = value;
     this.aplicarFiltros();
   }
 
   onContratoChange(event: Event): void {
     const value = (event.target as HTMLSelectElement).value;
     this.filtroContrato = value;
     this.aplicarFiltros();
   }
 
   // ViewChilds para resetear visualmente los <select>
   @ViewChild('categoriaSelect') categoriaSelect!: ElementRef<HTMLSelectElement>;
   @ViewChild('empresaSelect') empresaSelect!: ElementRef<HTMLSelectElement>;
   @ViewChild('contratoSelect') contratoSelect!: ElementRef<HTMLSelectElement>;
 
   // Limpia todos los filtros y recarga el listado completo
   limpiarFiltros(): void {
     this.filtroCategoria = '';
     this.filtroEmpresa = '';
     this.filtroContrato = '';
     this.cargarVacantes();
 
     if (this.categoriaSelect) this.categoriaSelect.nativeElement.value = '';
     if (this.empresaSelect) this.empresaSelect.nativeElement.value = '';
     if (this.contratoSelect) this.contratoSelect.nativeElement.value = '';
   }
 
   // Navega a la vista de detalle de una vacante
   saberMas(idVacante: number): void {
     this.router.navigate(['/candidato/vacantes', idVacante]);
   }
 }