import { Component, EventEmitter, Input, Output } from '@angular/core';
import Swal from 'sweetalert2';

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
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción cancelará la solicitud.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#f4c542',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, cancelar',
      cancelButtonText: 'No'
    }).then((result) => {
      if (result.isConfirmed) {
        this.cancelar.emit(this.id);
        Swal.fire({
          title: 'Cancelado',
          text: 'La solicitud ha sido cancelada.',
          icon: 'success',
          confirmButtonColor: '#f4c542'
        });
      }
    });
  }
}
