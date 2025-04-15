import { Component, inject } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { SolicitudesService } from '../../../core/services/solicitudes.service';
import { BotonesSolicitudEmpresaComponent } from "../../components/botones-solicitud-empresa/botones-solicitud-empresa.component";
import { ISolicitud } from '../../../core/interfaces/ISolicitud';
import { IUsuario } from '../../../core/interfaces/iusuario';

@Component({
  selector: 'app-solicitudes-page',
  imports: [BotonesSolicitudEmpresaComponent],
  templateUrl: './solicitudes-page.component.html',
  styleUrl: './solicitudes-page.component.css'
})
export class SolicitudesPageComponent {

  arraySolicitudes: ISolicitud[] = [];
  solicitudService = inject(SolicitudesService);

  constructor() {}

  ngOnInit() {

    // Cargamos las solicitudes de la empresa
    this.solicitudService.cargarMisSolicitudes();  
  
    // Nos suscribimos a los cambios
    this.solicitudService.solicitudes$.subscribe({
      next: (solicitudes) => {
        // Ordenamos por idCategoria antes de guardar en el array
        this.arraySolicitudes = solicitudes.sort((a, b) => a.idVacante - b.idVacante);
      },
      error: (err) => {
        console.error('Error al cargar solicitudes del usuario:', err);
      }
    });
  }
  
}