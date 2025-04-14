import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CategoriasService } from '../../../core/services/categorias.service';
import { ICategoria } from '../../../core/interfaces/ICategoria';

@Component({
  selector: 'app-categoria-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './categoria-form.component.html',
  styleUrls: ['./categoria-form.component.css'], // 👈 Corregido (antes estaba mal escrito)
})
export class CategoriaFormComponent {
  categoriaForm: FormGroup;
  submitted = false;

  constructor(
    private fb: FormBuilder,
    private categoriasService: CategoriasService,
    public router: Router
  ) {
    this.categoriaForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
    });
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.categoriaForm.invalid) return;

    const nuevaCategoria: ICategoria = this.categoriaForm.value;

    this.categoriasService.createCategoria(nuevaCategoria).subscribe({
      next: () => {
        alert('✅ ¡Categoría creada correctamente!');
        this.router.navigate(['/admin/categorias']);
      },
      error: (err) => {
        console.error(err);
        alert('❌ Error al crear la categoría');
      },
    });
  }
}
