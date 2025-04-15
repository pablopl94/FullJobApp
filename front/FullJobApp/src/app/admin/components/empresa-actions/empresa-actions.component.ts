import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IEmpresa } from '../../../core/interfaces/IEmpresa';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-empresa-actions',
  standalone: true,
  imports: [CommonModule, FormsModule  ],
  templateUrl: './empresa-actions.component.html',
  styleUrl: './empresa-actions.component.css'
})
export class EmpresaActionsComponent {

  @Input() empresa!: IEmpresa;
  @Input() mode: 'detalles' | 'editar' | 'alta' = 'detalles';
  @Output() onGuardar = new EventEmitter<IEmpresa>();
  @Output() onCancelar = new EventEmitter<void>();

  empresaData: IEmpresa = { ...this.empresa };

  ngOnChanges() {
    this.empresaData = { ...this.empresa };
  }

  guardarCambios() {
    this.onGuardar.emit(this.empresaData);
  }

  cancelar() {
    this.onCancelar.emit();
  }
}
