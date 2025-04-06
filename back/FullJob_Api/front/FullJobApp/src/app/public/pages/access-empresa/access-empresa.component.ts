import { Component } from '@angular/core';
import { PublicNavComponent } from '../../components/public-nav/public-nav.component';
import { RegistroInfoComponent } from '../../components/auth/registro-info/registro-info.component';
import { LoginFormComponent } from '../../components/auth/login-form/login-form.component';


@Component({
  standalone: true,
  selector: 'app-access-empresa',
  imports: [RegistroInfoComponent, LoginFormComponent, PublicNavComponent],
  templateUrl: './access-empresa.component.html',
  styleUrl: './access-empresa.component.css'
})
export class AccessEmpresaComponent {
  tipoSeleccionado: 'empresa' = 'empresa';


}
