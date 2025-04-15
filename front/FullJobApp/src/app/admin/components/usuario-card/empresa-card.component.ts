import { Component, Input } from '@angular/core';
import { IEmpresa } from '../../../core/interfaces/IEmpresa';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-empresa-card',
  imports: [CommonModule],
  templateUrl: './empresa-card.component.html',
  styleUrl: './empresa-card.component.css'
})
export class EmpresaCardComponent {
  @Input() empresa!: IEmpresa;

}
