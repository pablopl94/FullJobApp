import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ICategoria } from '../../../core/interfaces/ICategoria';

@Component({
  selector: 'app-categoria-actions',
  imports: [],
  templateUrl: './categoria-actions.component.html',
  styleUrl: './categoria-actions.component.css'
})
export class CategoriaActionsComponent {

  @Input() categoria!: ICategoria;
  @Output() onEdit = new EventEmitter<ICategoria>();
  @Output() onDelete = new EventEmitter<number>();

  editar(): void {
    this.onEdit.emit(this.categoria);
  }

  eliminar(): void {
    this.onDelete.emit(this.categoria.idCategoria);
  }
}
