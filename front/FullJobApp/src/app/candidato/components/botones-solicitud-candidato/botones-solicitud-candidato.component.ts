import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-botones-solicitud-candidato',
  imports: [],
  templateUrl: './botones-solicitud-candidato.component.html',
  styleUrl: './botones-solicitud-candidato.component.css'
})
export class BotonesSolicitudCandidatoComponent {
  @Input() id!: number;
  @Input() estado: string = '';

  @Output() saberMas = new EventEmitter<number>();
  @Output() cancelar = new EventEmitter<number>();

  onSaberMas(): void {
   this.saberMas.emit(this.id);
  }

  onCancelar(): void {
    this.cancelar.emit(this.id);
  }
}
