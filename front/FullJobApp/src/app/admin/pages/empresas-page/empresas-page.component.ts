import { Component, OnInit } from '@angular/core';
import { EmpresaService } from '../../../core/services/empresa.service';
import { IEmpresa } from '../../../core/interfaces/IEmpresa';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmpresaActionsComponent } from '../../components/empresa-actions/empresa-actions.component';

@Component({
  selector: 'app-empresas-page',
  imports: [RouterModule, CommonModule, EmpresaActionsComponent, FormsModule],
  templateUrl: './empresas-page.component.html',
  styleUrl: './empresas-page.component.css',
})
export class EmpresasPageComponent implements OnInit {
  empresas: IEmpresa[] = [];
  selectedEmpresa?: IEmpresa;
  showActions = false;
  mode: 'detalles' | 'editar' | 'alta' = 'detalles';

  constructor(private empresaService: EmpresaService, private router: Router) {}

  ngOnInit(): void {
    this.cargarEmpresas();
  }

  cargarEmpresas() {
    this.empresaService.getEmpresas().subscribe({
      next: (data) => {
        console.log('Empresas cargadas:', data);
        this.empresas = data;
      },
      error: (err) => console.error('Error al cargar empresas', err),
    });
  }
  
  abrirAccion(empresa: IEmpresa, modo: 'detalles' | 'editar') {
    this.selectedEmpresa = empresa;
    this.mode = modo;
    this.showActions = true;
  }

  nuevaEmpresa() {
    this.selectedEmpresa = {
      idEmpresa: 0,
      nombreEmpresa: '',
      cif: '',
      direccionFiscal: '',
      pais: '',
      fechaRegistro: new Date().toISOString().substring(0, 10),
      enabled: 1,
    };
    this.mode = 'alta';
    this.showActions = true;
  }

  cerrarAcciones() {
    this.showActions = false;
    this.selectedEmpresa = undefined;
  }

  guardarEmpresa(empresa: IEmpresa) {
    if (this.mode === 'alta') {
      this.empresaService.crearEmpresa(empresa).subscribe({
        next: () => {
          setTimeout(() => {
            this.cargarEmpresas();
            this.cerrarAcciones();
          }, 300); 
        },
        error: (e) => console.error('Error al crear empresa', e),
      });
    } else {
      this.empresaService.actualizarEmpresa(empresa).subscribe({
        next: () => {
          this.cargarEmpresas();
          this.cerrarAcciones();
        },
        error: (e) => console.error('Error al actualizar empresa', e),
      });
    }
  }

  eliminarEmpresa(id: number) {
    if (confirm('¿Estás seguro de que deseas eliminar esta empresa?')) {
      this.empresaService.eliminarEmpresa(id).subscribe(() => {
        this.empresas = this.empresas.filter((e) => e.idEmpresa !== id);
      });
    }
  }
}
