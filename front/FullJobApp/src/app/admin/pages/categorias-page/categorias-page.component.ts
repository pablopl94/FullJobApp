import { Component } from '@angular/core';
import { CategoriasService } from '../../../core/services/categorias.service';
import { ICategoria } from '../../../core/interfaces/ICategoria';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-categorias-page',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './categorias-page.component.html',
  styleUrl: './categorias-page.component.css'
})
export class CategoriasPageComponent {

  categorias: ICategoria[] = [];

  constructor(private categoriaService: CategoriasService) {}

  ngOnInit(): void {
    this.cargarCategorias();
  }

  cargarCategorias(): void {
    this.categoriaService.getCategorias().subscribe(data => {
      this.categorias = data;
    });
  }

  eliminarCategoria(id: number) {
    if (confirm('¿Estás seguro de que deseas eliminar esta categoria?')) {
      this.categoriaService.eliminarCategoria(id).subscribe(() => {
        this.categorias = this.categorias.filter(e => e.idCategoria !== id);
      });
    }
  }

  obtenerCategorias(): void {
    this.categoriaService.getCategorias().subscribe({
      next: (categorias) => (this.categorias = categorias),
      error: (err) => console.error('Error cargando categorías:', err),
    });
  }
}
