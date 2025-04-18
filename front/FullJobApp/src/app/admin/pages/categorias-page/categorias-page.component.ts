import { ChangeDetectorRef, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriasService } from '../../../core/services/categorias.service';
import { ICategoria } from '../../../core/interfaces/ICategoria';
import { CategoriaFormComponent } from '../../components/categoria-form/categoria-form.component';
import { CategoriaActionsComponent } from '../../components/categoria-actions/categoria-actions.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-categorias-page',
  standalone: true,
  imports: [CommonModule, CategoriaFormComponent, CategoriaActionsComponent],
  templateUrl: './categorias-page.component.html',
  styleUrl: './categorias-page.component.css',
})
export class CategoriasPageComponent {
  categorias: ICategoria[] = [];
  mostrarFormulario = false;
  categoriaSeleccionada?: ICategoria;

  constructor(private categoriaService: CategoriasService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.cargarCategorias();
  }

  cargarCategorias(): void {
    this.categoriaService.getCategorias().subscribe({
      next: (data) => {
        this.categorias = data;
        this.cdr.detectChanges();
      },
    });
  }

  nuevaCategoria(): void {
    this.categoriaSeleccionada = undefined;
    this.mostrarFormulario = true;
  }

  onFormularioGuardado(): void {
    this.mostrarFormulario = false;
    this.cargarCategorias();
  }
}
