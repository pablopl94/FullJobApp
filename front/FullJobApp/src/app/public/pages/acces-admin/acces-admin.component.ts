import { Component } from '@angular/core';
import { PublicNavComponent } from "../../components/public-nav/public-nav.component";
import { RegistroInfoComponent } from "../../components/auth/registro-info/registro-info.component";
import { LoginFormComponent } from "../../components/auth/login-form/login-form.component";

@Component({
  selector: 'app-acces-admin',
  standalone: true,
  imports: [PublicNavComponent, RegistroInfoComponent, LoginFormComponent],
  templateUrl: './acces-admin.component.html',
  styleUrl: './acces-admin.component.css'
})
export class AccesAdminComponent {

}
