import { Component } from '@angular/core';
import { UsuarioCardComponent } from "../../components/usuario-card/usuario-card.component";

@Component({
  selector: 'app-candidatos-page',
  imports: [UsuarioCardComponent],
  templateUrl: './candidatos-page.component.html',
  styleUrl: './candidatos-page.component.css'
})
export class CandidatosPageComponent {

  // arrayCliente: ICliente[];


  // @ngOnit{
  //   this.clienteService.obtenerClientes().subscribe((data: any[]) => {
  //     this.arrayCliente = data;
  //   }
  // }
}
