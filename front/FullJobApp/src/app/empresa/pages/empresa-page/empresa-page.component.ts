import { Component } from '@angular/core';

import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../../../components/navbar/navbar.component';

@Component({
  selector: 'app-empresa-page',
  imports: [RouterModule, NavbarComponent],
  templateUrl: './empresa-page.component.html',
  styleUrl: './empresa-page.component.css'
})
export class EmpresaPageComponent {

}
