import { Component, inject, Input } from '@angular/core';

import {RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { SolicitudesService } from '../../../core/services/solicitudes.service';

@Component({
  standalone: true,
  selector: 'app-botones-solicitud-empresa',
  templateUrl: './botones-solicitud-empresa.component.html',
  imports: [RouterLink],
  styleUrl: './botones-solicitud-empresa.component.css'

})
export class BotonesSolicitudEmpresaComponent {

  @Input() solicitudId!: number;
  @Input() parent: string;
  
  solicitudesService = inject(SolicitudesService);

  constructor() {
    this.parent = "";
  }

  asignar(): void {
    if (!this.solicitudId) return;

    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Vas a asignar esta vacante al candidato.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, asignar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#6c757d'
    }).then((result) => {
      if (result.isConfirmed) {
        this.solicitudesService.asignarVacante(this.solicitudId).subscribe({
          next: (message) => {
            Swal.fire('✅ Asignado', message, 'success');
          },
          error: (err) => {
            console.error('❌ Error al asignar:', err);
            Swal.fire('Error', 'La vacante ya tiene una solicitud asignada', 'error');
          }
        });
      }
    });
  }
}