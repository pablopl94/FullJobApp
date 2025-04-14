import { Component, inject } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { SolicitudesService } from '../../../core/services/solicitudes.service';
import { BotonesSolicitudEmpresaComponent } from "../../components/botones-solicitud-empresa/botones-solicitud-empresa.component";
import { ISolicitud } from '../../../core/interfaces/ISolicitud';
import { IUsuario } from '../../../core/interfaces/IUsuario';

@Component({
  selector: 'app-solicitudes-page',
  imports: [BotonesSolicitudEmpresaComponent],
  templateUrl: './solicitudes-page.component.html',
  styleUrl: './solicitudes-page.component.css'
})
export class SolicitudesPageComponent {

  arraySolicitudes: ISolicitud[] = [];
  usuario !: IUsuario;
  authService = inject(AuthService);
  solicitudService = inject(SolicitudesService);

  constructor() {}

  ngOnInit() {

    //Cargamos las solicitudes de la empresa
    this.solicitudService.cargarMisSolicitudes();  

   //Nos suscribimos a los cambios que pueda tener con subject
    this.solicitudService.solicitudes$.subscribe({
    next: (solicitudes) => {
      this.arraySolicitudes = solicitudes;
    },
    error: (err) => {
      console.error('Error al cargar solicitudes del usuario:', err);
    }
  });
  }
}