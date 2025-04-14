import { Component, inject } from '@angular/core';
import { SolicitudesService } from '../../../core/services/solicitudes.service';
import { AuthService } from '../../../core/services/auth.service';
import { BotonesSolicitudEmpresaComponent } from "../../components/botones-solicitud-empresa/botones-solicitud-empresa.component";
import { ISolicitud } from '../../../core/interfaces/ISolicitud';
import { IEmpresa } from '../../../core/interfaces/IEmpresa';
import { EmpresaService } from '../../../core/services/empresa.service';


@Component({
  selector: 'app-home-page',
  imports: [BotonesSolicitudEmpresaComponent],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {

  ultimasSolicitudes: ISolicitud[] = [];
  private solicitudesService = inject(SolicitudesService);
  miEmpresa!: string; 
  authService = inject(AuthService);
  empresaService = inject(EmpresaService);

  constructor() {}

  ngOnInit(): void {
    //cargamos los datos de usuario para la home
     this.empresaService.getDetallesEmpresaAutenticada().subscribe({
      next:(empresa) =>{
        this.miEmpresa = empresa.nombreEmpresa
      },
      error: (error) =>{
        console.error(`Error al cargar el nombre del empresa ` + error);
      }
     });
    

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

