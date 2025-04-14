import { Component, Input } from '@angular/core';
import { BotonesSolicitudEmpresaComponent } from "../botones-solicitud-empresa/botones-solicitud-empresa.component";
import { ISolicitud } from '../../../core/interfaces/ISolicitud';

@Component({
  standalone: true,
  selector: 'app-solicitud-empresa-card',
  imports: [BotonesSolicitudEmpresaComponent],
  templateUrl: './solicitud-empresa-card.component.html',
  styleUrl: './solicitud-empresa-card.component.css'
})
export class SolicitudEmpresaCardComponent {

  @Input() miSolicitud !: ISolicitud;

  
}
