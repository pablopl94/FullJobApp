import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { SolicitudesService } from '../../../core/services/solicitudes.service';
import { AuthService } from '../../../core/services/auth.service';
import { ISolicitud } from '../../../core/interfaces/ISolicitud';
import { IVacante } from '../../../core/interfaces/IVacante';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-vacante-card',
  imports: [FormsModule],
  templateUrl: './vacante-card.component.html',
  styleUrl: './vacante-card.component.css'
})
export class VacanteCardComponent {

  @Input() vacante!: IVacante;

  @Output() saberMas = new EventEmitter<number>();

  onSaberMas(): void {
    this.saberMas.emit(this.vacante.idVacante);
  }

}
