import { Component } from '@angular/core';
import { RegistroInfoComponent } from "../../../components/auth/registro-info/registro-info/registro-info.component";
import { LoginFormComponent } from "../../../components/auth/login-form/login-form/login-form.component";

@Component({
  standalone: true,
  selector: 'app-access-empresa',
  imports: [RegistroInfoComponent, LoginFormComponent],
  templateUrl: './access-empresa.component.html',
  styleUrl: './access-empresa.component.css'
})
export class AccessEmpresaComponent {

}
