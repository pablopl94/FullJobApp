import { Component, inject } from '@angular/core';
import { SolicitudesService } from '../../../core/services/solicitudes.service';
import { AuthService } from '../../../core/services/auth.service';
import { BotonesSolicitudEmpresaComponent } from "../../components/botones-solicitud-empresa/botones-solicitud-empresa.component";
import { ISolicitud } from '../../../core/interfaces/ISolicitud';
import { IUsuario } from '../../../core/interfaces/IUsuario';


@Component({
  selector: 'app-home-page',
  imports: [BotonesSolicitudEmpresaComponent],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {

  ultimasSolicitudes: ISolicitud[] = [];
  private solicitudesService = inject(SolicitudesService);
  usuario!: IUsuario; 
  authService = inject(AuthService);

  constructor() {}

  ngOnInit(): void {
    //cargamos los datos de usuario para la home
    this.usuario = this.authService.obtenerUsuario();

    //Cargamos las ultimas solicitudes
    this.solicitudesService.cargarUltimasSolicitudes();
  
    //Nos suscribimos a los cambios con subject
    this.solicitudesService.ultimasSolicitudes$.subscribe({
      next: (solicitudes) => {
        this.ultimasSolicitudes = solicitudes;
      },
      error: (err) => {
        console.error(' Error al cargar las últimas solicitudes:', err);
      }
    });
  }

}
  



