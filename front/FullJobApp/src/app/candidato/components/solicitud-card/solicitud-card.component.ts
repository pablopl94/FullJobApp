import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ISolicitud } from '../../../core/interfaces/ISolicitud';
import { BotonesSolicitudCandidatoComponent } from '../botones-solicitud-candidato/botones-solicitud-candidato.component';

@Component({
  selector: 'app-solicitud-card',
  imports: [BotonesSolicitudCandidatoComponent],
  standalone: true,
  templateUrl: './solicitud-card.component.html',
  styleUrl: './solicitud-card.component.css'
})
export class SolicitudCardComponent {
  @Input() solicitud!: ISolicitud;

  @Output() cancelar = new EventEmitter<number>();
  @Output() saberMas = new EventEmitter<number>();

  onCancelar(): void {
    this.cancelar.emit(this.solicitud.idSolicitud);
  }

  onSaberMas(): void {
    this.saberMas.emit(this.solicitud.idSolicitud);
  }

}
