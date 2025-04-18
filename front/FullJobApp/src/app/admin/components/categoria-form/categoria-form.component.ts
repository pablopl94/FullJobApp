import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ICategoria } from '../../../core/interfaces/ICategoria';
import { CategoriasService } from '../../../core/services/categorias.service';
// ...

@Component({
  selector: 'app-categoria-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './categoria-form.component.html',
  styleUrls: ['./categoria-form.component.css'],
})
export class CategoriaFormComponent implements OnInit {
  @Input() categoria?: ICategoria;
  @Output() formularioGuardado = new EventEmitter<void>();

  categoriaForm!: FormGroup;
  modoEdicion = false;

  constructor(
    private fb: FormBuilder,
    private categoriaService: CategoriasService
  ) {}

  ngOnInit(): void {
    this.categoriaForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(100)]],
      descripcion: ['', [Validators.required, Validators.maxLength(2000)]]
    });

    if (this.categoria) {
      this.modoEdicion = true;
      this.categoriaForm.patchValue(this.categoria);
    }
  }

  guardar(): void {
    if (this.categoriaForm.invalid) return;

    const categoriaData = this.categoriaForm.value;
    const request = this.modoEdicion
      ? this.categoriaService.actualizarCategoria(this.categoria!.idCategoria, categoriaData)
      : this.categoriaService.crearCategoria(categoriaData);

    request.subscribe(() => {
      this.formularioGuardado.emit();
    });
  }
}
