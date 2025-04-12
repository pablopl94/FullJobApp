import { Component, inject } from '@angular/core';
import { SolicitudEmpresaCardComponent } from "../../components/solicitud-empresa-card/solicitud-empresa-card.component";
import { ISolicitud } from '../../../core/interfaces/isolicitud';
import { IUsuario } from '../../../core/interfaces/iusuario';
import { AuthService } from '../../../core/services/auth.service';
import { SolicitudesService } from '../../../core/services/solicitudes.service';

@Component({
  selector: 'app-solicitudes-page',
  imports: [SolicitudEmpresaCardComponent],
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
    //Obtenemos primero el usuario
    this.usuario = this.authService.obtenerUsuario();

    //Cargamos las solicitudes de la empresa autenticada
    this.solicitudService.obtenerMisSolicitudes().subscribe((solicitudes: ISolicitud[]) => {
      this.arraySolicitudes = solicitudes;
    });
  }
}