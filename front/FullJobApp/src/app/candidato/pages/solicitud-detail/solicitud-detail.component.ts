import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ISolicitud } from '../../../core/interfaces/ISolicitud';
import { SolicitudesService } from '../../../core/services/solicitudes.service';

@Component({
  selector: 'app-solicitud-detail',
  imports: [],
  templateUrl: './solicitud-detail.component.html',
  styleUrl: './solicitud-detail.component.css'
})
export class SolicitudDetailComponent {
  activatedRoute = inject(ActivatedRoute);
  solicitudesService = inject(SolicitudesService);

  miSolicitud: ISolicitud | null = null;
  route: any;
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      const id = params['id'];
  
      this.solicitudesService.obtenerDetalleSolicitudComoCandidato(id).subscribe({
        next: (solicitud) => {
          this.miSolicitud = solicitud;
          console.log('solicitud', this.miSolicitud);
        },
        error: (err) => {
          console.error('Error al cargar la solicitud del candidato', err);
        }
      });
    });
  }
}
