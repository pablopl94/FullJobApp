import { Component } from '@angular/core';
import { RegistroInfoComponent } from "../../../components/auth/registro-info/registro-info/registro-info.component";
import { LoginFormComponent } from "../../../components/auth/login-form/login-form/login-form.component";

@Component({
  standalone: true,
  selector: 'app-access-candidato',
  imports: [RegistroInfoComponent, LoginFormComponent],
  templateUrl: './access-candidato.component.html',
  styleUrl: './access-candidato.component.css'
})
export class AccessCandidatoComponent {

}
