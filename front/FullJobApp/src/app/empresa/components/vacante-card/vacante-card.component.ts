
import { Component, Input } from '@angular/core';
import { IVacante } from '../../../core/interfaces/IVacante';
import { BotonesVacanteEmpresaComponent } from "../botones-vacante-empresa/botones-vacante-empresa.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-vacante-card',
  standalone: true,
  imports: [CommonModule ,BotonesVacanteEmpresaComponent],
  templateUrl: './vacante-card.component.html',
  styleUrl: './vacante-card.component.css'
})
export class VacanteCardComponent {
  
  @Input() titulo: string = '';
  @Input() vacantes: IVacante[] = [];
  @Input() mostrarOpciones: boolean = false;
}