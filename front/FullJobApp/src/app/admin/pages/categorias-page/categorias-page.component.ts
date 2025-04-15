import { Component } from '@angular/core';
import { CategoriasService } from '../../../core/services/categorias.service';
import { ICategoria } from '../../../core/interfaces/ICategoria';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CategoriaActionsComponent } from '../../components/categoria-actions/categoria-actions.component';

@Component({
  selector: 'app-categorias-page',
  standalone: true,
  imports: [RouterModule, CategoriaActionsComponent],
  templateUrl: './categorias-page.component.html',
  styleUrl: './categorias-page.component.css',
})
export class CategoriasPageComponent {
  categorias: ICategoria[] = [];

  constructor(
    private categoriaService: CategoriasService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.cargarCategorias();
  }

  cargarCategorias(): void {
    this.categoriaService.getAll().subscribe({
      next: (data) => {
        this.categorias = data; 
      },
      error: (error) => {
        console.error('Error al cargar categorías', error);
      },
    });
  }
  

  eliminarCategoria(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar esta categoría?')) {
      this.categoriaService.delete(id).subscribe({
        next: () => {
          this.cargarCategorias(); 
        },
        error: (err) => {
          console.error('Error al eliminar categoría', err);
        },
      });
    }
  }
  
  editarCategoria(categoria: ICategoria) {
    this.router.navigate(['editar', categoria.idCategoria], {
      relativeTo: this.route,
    });
  }
}
