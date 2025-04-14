import { Component } from '@angular/core';
import { LoginFormComponent } from "../../components/auth/login-form/login-form.component";

@Component({
  selector: 'app-acces-admin',
  standalone: true,
  imports: [LoginFormComponent],
  templateUrl: './acces-admin.component.html',
  styleUrl: './acces-admin.component.css'
})
export class AccesAdminComponent {

}
