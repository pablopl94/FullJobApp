import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { VacantesService } from '../../../core/services/vacantes.service';
import { CategoriasService } from '../../../core/services/categorias.service';
import { IVacante } from '../../../core/interfaces/IVacante';
import { ICategoria } from '../../../core/interfaces/ICategoria';


@Component({
  selector: 'app-vacante-form',
  imports: [ReactiveFormsModule],
  templateUrl: './vacante-form.component.html',
  styleUrl: './vacante-form.component.css'
})

export class VacanteFormComponent implements OnInit {

  router = inject(Router);
  vacanteService = inject(VacantesService);
  categoriaService = inject(CategoriasService);
  activatedRoute = inject(ActivatedRoute);

  vacanteForm!: FormGroup;
  tipo: string;
  arrayCategorias!: ICategoria[];
  arrayTiposContrato!: string[];

  constructor() {
    this.tipo = "";

    // Inicializamos el formulario vacío
    this.vacanteForm = new FormGroup({
      idVacante: new FormControl(''),
      idCategoria: new FormControl(''),
      nombre: new FormControl(''),
      descripcion: new FormControl(''),
      detalles: new FormControl(''),
      fecha: new FormControl(''),
      salario: new FormControl(''),
      estatus: new FormControl(''),
      destacado: new FormControl(''),
      imagen: new FormControl('')
    });
  }

  // Cargar datos de una vacante por ID
  cargarVacante(idVacante: number) {
    console.log('[cargarVacante] ID recibido:', idVacante);
    this.vacanteService.findById(idVacante).subscribe({
      next: (vacante) => {
        console.log('[cargarVacante] Vacante encontrada:', vacante);

        this.tipo ==='Modificar';

        this.vacanteForm.patchValue({
          idVacante: vacante.idVacante,
          idCategoria: vacante.idCategoria , // Asegúrate que tenga un valor válido
          nombre: vacante.nombre,
          descripcion: vacante.descripcion,
          detalles: vacante.detalles,
          fecha: vacante.fecha,
          salario: vacante.salario,
          estatus: vacante.estatus,
          destacado: vacante.destacado,
          imagen: vacante.imagen || ''
        });
        console.log('[cargarVacante] Formulario cargado:', this.vacanteForm.value);
      },
      error: (error) => {
        console.error('[cargarVacante] Error al cargar vacante:', error);
      }
    });
  }

  // Cargar categorías
  cargarCategorias() {
    this.categoriaService.getCategorias().subscribe({
      next: (categorias) => {
        this.arrayCategorias = categorias;
        console.log('[cargarCategorias] Categorías cargadas:', categorias);
      },
      error: (error) => {
        console.error('[cargarCategorias] Error al cargar categorías:', error);
      }
    });
  }

  // Cargar tipos de contrato
  cargarTiposContrato() {
    this.vacanteService.getTiposContrato().subscribe({
      next: (tiposContrato) => {
        this.arrayTiposContrato = tiposContrato;
        console.log('[cargarTiposContrato] Tipos de contrato cargados:', tiposContrato);
      },
      error: (error) => {
        console.error('[cargarTiposContrato] Error al cargar tipos de contrato:', error);
      }
    });
  }

  // Redirigir a la página principal de vacantes
  backToHome() {
    this.router.navigate(['/empresa/vacantes']);
  }

  // Inicializar componente
  ngOnInit(): void {
    this.cargarCategorias();
    this.cargarTiposContrato();

    this.activatedRoute.params.subscribe((params: any) => {
      if (params.idVacante) {
        console.log('[ngOnInit] Se detectó idVacante:', params.idVacante);
        this.cargarVacante(params.idVacante);
      } else {
        console.log('[ngOnInit] No se recibió idVacante, modo creación');
        this.tipo = 'Crear';
      }
    });
  }

  // Enviar formulario
  getDataForm() {
    const vacante: IVacante = this.vacanteForm.value;
    console.log(vacante.idCategoria);

    if (vacante.idVacante) {
      console.log('[getDataForm] Modo: Modificar');
      this.vacanteService.actualizarVacante(vacante).subscribe({
        next: () => {
          alert('Vacante actualizada correctamente');
          this.backToHome();
        },
        error: (error) => {
          console.error('[getDataForm] Error al actualizar:', error);
        }
      });
    } else {
      console.log('[getDataForm] Modo: Crear');
      this.vacanteService.publicarVacante(vacante).subscribe({
        next: () => {
          alert('Vacante publicada correctamente');
          this.vacanteForm.reset();
          this.backToHome();
        },
        error: (error) => {
          console.error('[getDataForm] Error al publicar:', error);
        }
      });
    }
  }
}