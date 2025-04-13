import { Component } from '@angular/core';
import { EmpresaService } from '../../../core/services/empresa.service';
import { IEmpresa } from '../../../core/interfaces/IEmpresa';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-empresas-page',
  imports: [RouterModule, CommonModule],
  templateUrl: './empresas-page.component.html',
  styleUrl: './empresas-page.component.css'
})
export class EmpresasPageComponent {

  empresas: IEmpresa[] = [];

  constructor(private empresaService: EmpresaService, private router: Router) {}

  ngOnInit(): void {
    this.cargarEmpresas();
  }

  cargarEmpresas() {
    this.empresaService.getEmpresas().subscribe({
      next: (data) => this.empresas = data,
      error: (err) => console.error('Error al cargar empresas', err)
    });
  }

  eliminarEmpresa(id: number) {
    if (confirm('¿Estás seguro de que deseas eliminar esta empresa?')) {
      this.empresaService.eliminarEmpresa(id).subscribe(() => {
        this.empresas = this.empresas.filter(e => e.idEmpresa !== id);
      });
    }
  }

  verDetalles(id: number): void {
    this.router.navigate(['/admin/empresas/detalles', id]);
  }
}
