import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../../../components/navbar/navbar.component';
import { FooterComponent } from "../../../components/footer/footer.component";

@Component({
  selector: 'app-candidato-page',
  imports: [RouterModule, NavbarComponent, FooterComponent],
  templateUrl: './candidato-page.component.html',
  styleUrl: './candidato-page.component.css'
})
export class CandidatoPageComponent {

}
