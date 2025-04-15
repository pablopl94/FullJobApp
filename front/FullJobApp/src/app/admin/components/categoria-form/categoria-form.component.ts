import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoriasService } from '../../../core/services/categorias.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-categoria-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './categoria-form.component.html',
  styleUrls: ['./categoria-form.component.css'],
})
export class CategoriaFormComponent implements OnInit {
  
  categoriaForm: FormGroup;
  idCategoria?: number;
  modoEdicion = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private categoriaService: CategoriasService
  ) {
    this.categoriaForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(100)]],
      descripcion: ['', [Validators.required, Validators.maxLength(2000)]],
    });
  }

  ngOnInit(): void {
    this.idCategoria = Number(this.route.snapshot.paramMap.get('id'));
    this.modoEdicion = !!this.idCategoria;
  
    if (this.modoEdicion) {
      this.categoriaService.getById(this.idCategoria).subscribe((categoria) => {
        this.categoriaForm.patchValue(categoria);
      });
    }
  }

  guardar(): void {
    if (this.categoriaForm.invalid) return;

    const datos = this.categoriaForm.value;

    if (this.modoEdicion && this.idCategoria) {
      this.categoriaService.update(this.idCategoria, datos).subscribe(() => {
        this.router.navigate(['/admin/categorias']);
      });
    } else {
      this.categoriaService.create(datos).subscribe(() => {
        this.router.navigate(['/admin/categorias']);
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/admin/categorias']);
  }

  get nombre() {
    return this.categoriaForm.get('nombre')!;
  }
  
  get descripcion() {
    return this.categoriaForm.get('descripcion')!;
  }
}
