import { Component, inject } from '@angular/core';
import { ISolicitud } from '../../../core/interfaces/ISolicitud';
import { SolicitudesService } from '../../../core/services/solicitudes.service';
import { AuthService } from '../../../core/services/auth.service';
import { SolicitudCardComponent } from '../../components/solicitud-card/solicitud-card.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-solicitudes-page',
  imports: [SolicitudCardComponent],
  templateUrl: './solicitudes-page.component.html',
  styleUrl: './solicitudes-page.component.css'
})
export class SolicitudesPageComponent {
  
  router = inject(Router);
  arraySolicitudes: ISolicitud[] = [];
  usuario: any = {};
  private solicitudService = inject(SolicitudesService);
  private authService = inject(AuthService);

  
  ngOnInit(): void {
    this.usuario = this.authService.obtenerUsuario();

    this.solicitudService.cargarMisSolicitudes();

    this.solicitudService.solicitudes$.subscribe({
      next: (solicitudes) => {
        this.arraySolicitudes = solicitudes.sort((a, b) => a.idVacante - b.idVacante);
      },
      error: (err) => {
        console.error('Error al cargar solicitudes del usuario:', err);
      }
    });
  }
  cancelarSolicitud(idSolicitud: number): void {
    this.solicitudService.cancelarSolicitud(idSolicitud).subscribe(() => {
      this.arraySolicitudes = this.arraySolicitudes.filter(s => s.idSolicitud !== idSolicitud);
    });
  }

  saberMas(idSolicitud: number): void {
  this.router.navigate([`/candidato/solicitudes`, idSolicitud]);
    
  }

}
