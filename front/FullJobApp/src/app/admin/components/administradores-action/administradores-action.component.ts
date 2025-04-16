import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IUsuario } from '../../../core/interfaces/iusuario';

@Component({
  selector: 'app-administradores-action',
  imports: [],
  templateUrl: './administradores-action.component.html',
  styleUrl: './administradores-action.component.css'
})
export class AdministradoresActionComponent {

  @Input() usuario!: IUsuario;
  @Output() onEdit = new EventEmitter<IUsuario>();
  @Output() onDelete = new EventEmitter<String>();

  editar() {
    this.onEdit.emit(this.usuario);
  }

  eliminar() {
    this.onDelete.emit(this.usuario.email);
  }
}
