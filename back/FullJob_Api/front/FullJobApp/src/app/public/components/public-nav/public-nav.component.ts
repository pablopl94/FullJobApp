import { Component, Output, EventEmitter } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-public-nav',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './public-nav.component.html',
  styleUrls: ['./public-nav.component.css']
})
export class PublicNavComponent {
  @Output() accesoSeleccionado = new EventEmitter<'empresa' | 'candidato'>();

  accederComo(tipo: 'empresa' | 'candidato') {
    this.accesoSeleccionado.emit(tipo);
  }
}
