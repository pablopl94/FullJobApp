import { Component, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ISolicitud } from '../../../core/interfaces/ISolicitud';
import { SolicitudesService } from '../../../core/services/solicitudes.service';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-solicitud-detail',
  imports: [RouterLink, NgClass],
  templateUrl: './solicitud-detail.component.html',
  styleUrl: './solicitud-detail.component.css'
})
export class SolicitudDetailComponent {

  activatedRoute = inject(ActivatedRoute);
  solicitudesService = inject(SolicitudesService);
  miSolicitud!: ISolicitud;


    //Metodo que obtiene el _id de la URL, se lo pasa al metodo del servicio para buscar por id
  // un usuario y lo guardamos en la variable miUsuario
  ngOnInit(): void {
    this.activatedRoute.params.subscribe({
      next: (params: any) => {
        const id = params.id;
        this.solicitudesService.obtenerDetalleSolicitud(id).subscribe({
          next: (solicitud) => {
            this.miSolicitud = solicitud;
          },
          error: (err) => console.error('Error al cargar la solicitud:', err)
        });
      }
    });
  }

 
}
