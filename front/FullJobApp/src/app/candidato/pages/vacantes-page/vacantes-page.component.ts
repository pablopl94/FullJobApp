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
  vacantes: IVacante[] = [];
  todasLasVacantes: IVacante[] = [];

  categorias: ICategoria[] = [];
  empresas: IEmpresa[] = [];
  contratos: string[] = [];

  filtroCategoria: number | '' = '';
  filtroEmpresa: string = '';
  filtroContrato: string = '';

  private vacanteService = inject(VacantesService);
  private categoriaService = inject(CategoriasService);
  private empresaService = inject(EmpresaService);
  private router = inject(Router);

  ngOnInit(): void {
    this.vacanteService.getVacantesCandidato().subscribe({
      next: (data) => {
        this.vacantes = data;
        this.todasLasVacantes = data;
        this.contratos = [...new Set(data.map(v => v.detalles))];
        console.log(`[VacantesPage] Vacantes recibidas: ${data.length}`);
        console.log('Todas las vacantes:', this.todasLasVacantes);
        console.log('Vacantes filtradas:', this.vacantes);
      },
      error: (err) => console.error('Error al cargar vacantes', err)
    });

    this.categoriaService.getAll().subscribe({
      next: (data) => this.categorias = data,
      error: (err) => console.error('Error al cargar categorías', err)
    });

    this.empresaService.getEmpresas().subscribe({
      next: (data) => this.empresas = data,
      error: (err) => console.error('Error al cargar empresas', err)
    });
  }

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



  aplicarFiltros(): void {
    console.log('[Tipo de v.idCategoria]', typeof this.todasLasVacantes[0]?.idCategoria);
    this.vacantes = this.todasLasVacantes.filter(vac => {
      console.log('Filtrando por categoría:', this.filtroCategoria);
      const matchCategoria = this.filtroCategoria !== '' ? Number(vac.idCategoria) === Number(this.filtroCategoria) : true;
      const matchEmpresa = this.filtroEmpresa !== '' ? vac.nombreEmpresa === this.filtroEmpresa : true; 
      const matchContrato = this.filtroContrato !== '' ? vac.detalles === this.filtroContrato : true;
      return matchCategoria && matchEmpresa && matchContrato;
    });
  }

  // Para limpiar el filtro de categoría, se necesita un ViewChild
  @ViewChild('categoriaSelect') categoriaSelect!: ElementRef<HTMLSelectElement>;
  @ViewChild('empresaSelect') empresaSelect!: ElementRef<HTMLSelectElement>;
  @ViewChild('contratoSelect') contratoSelect!: ElementRef<HTMLSelectElement>;

  limpiarFiltros(): void {
    this.filtroCategoria = '';
    this.filtroEmpresa = '';
    this.filtroContrato = '';
    this.vacantes = [...this.todasLasVacantes];

    if (this.categoriaSelect) this.categoriaSelect.nativeElement.value = '';
    if (this.empresaSelect) this.empresaSelect.nativeElement.value = '';
    if (this.contratoSelect) this.contratoSelect.nativeElement.value = '';
  }

  saberMas(idVacante: number): void {
    this.router.navigate(['/candidato/vacantes', idVacante]);
  }

  
}