import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SolicitudesService } from '../../../core/services/solicitudes.service';
import { BotonesSolicitudEmpresaComponent } from "../../components/botones-solicitud-empresa/botones-solicitud-empresa.component";
import { ISolicitud } from '../../../core/interfaces/ISolicitud';

@Component({
  standalone: true,
  selector: 'app-solicitud-details',
  imports: [BotonesSolicitudEmpresaComponent],
  templateUrl: './solicitud-details.component.html',
  styleUrl: './solicitud-details.component.css'
})
export class SolicitudDetailsComponent {

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
