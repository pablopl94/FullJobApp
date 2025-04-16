import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IEmpresa } from '../../../core/interfaces/IEmpresa';


@Component({
  selector: 'app-empresa-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './empresa-card.component.html',
})
export class EmpresaCardComponent {
  @Input() empresa!: IEmpresa;
  @Output() cerrar = new EventEmitter<void>();
}
