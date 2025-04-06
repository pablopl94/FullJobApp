import { Component } from '@angular/core';
import { PublicNavComponent } from '../../components/public-nav/public-nav.component';
import { LoginFormComponent } from '../../components/auth/login-form/login-form.component';
import { RegistroInfoComponent } from '../../components/auth/registro-info/registro-info.component';


@Component({
  selector: 'app-access-candidato',
  standalone: true,
  imports: [LoginFormComponent, RegistroInfoComponent, PublicNavComponent],
  templateUrl: './access-candidato.component.html',
  styleUrls: ['./access-candidato.component.css']
})
export class AccessCandidatoComponent {
  tipoSeleccionado: 'candidato' = 'candidato';
}
