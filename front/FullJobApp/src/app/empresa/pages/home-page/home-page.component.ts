import { Component, inject } from '@angular/core';
import { SolicitudEmpresaCardComponent } from "../../components/solicitud-empresa-card/solicitud-empresa-card.component";
import { SolicitudesService } from '../../../core/services/solicitudes.service';
import { ISolicitud } from '../../../core/interfaces/isolicitud';
import { IUsuario } from '../../../core/interfaces/iusuario'; // Adjust the path if necessary
import { AuthService } from '../../../core/services/auth.service';


@Component({
  selector: 'app-home-page',
  imports: [SolicitudEmpresaCardComponent],
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

    //Cargamos las ultimas solicitudes para la tabla del home
    this.solicitudesService.obtenerUltimasSolicitudes().subscribe({
      next: (solicitudes) => this.ultimasSolicitudes = solicitudes,
      error: (err) => console.error('Error al cargar las solicitudes:', err)
    });
  }

}
  



