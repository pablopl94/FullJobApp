import { Component } from '@angular/core';

import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../../../components/navbar/navbar.component';
import { FooterComponent } from "../../../components/footer/footer.component";

@Component({
  selector: 'app-empresa-page',
  imports: [RouterModule, NavbarComponent, FooterComponent],
  templateUrl: './empresa-page.component.html',
  styleUrl: './empresa-page.component.css'
})
export class EmpresaPageComponent {


  
}
