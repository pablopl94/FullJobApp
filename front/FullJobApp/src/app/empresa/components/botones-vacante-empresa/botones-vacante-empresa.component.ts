import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IVacante } from '../../../core/interfaces/IVacante';
import { VacantesService } from '../../../core/services/vacantes.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-botones-vacante-empresa',
  imports: [RouterLink],
  templateUrl: './botones-vacante-empresa.component.html',
  styleUrl: './botones-vacante-empresa.component.css'
})
export class BotonesVacanteEmpresaComponent {

  @Input() miId !: number;
  vacanteService = inject(VacantesService)
  


  cancelar() {
    if (!this.miId) return;

    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Vas a cancelar la vacante',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Cancelar',
      cancelButtonText: 'No cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#6c757d'
    }).then((result) => {
      if (result.isConfirmed) {
        this.vacanteService.cancelarVacante(this.miId).subscribe({
          next: (message) => {
            Swal.fire('✅ Cancelada', message , 'success');
          },
          error: (err) => {
            console.error('❌ Error al asignar:', err);
            Swal.fire('Error', 'No se pudo asignar la vacante', 'error');
          }
        });
      }
    });
  }
}
