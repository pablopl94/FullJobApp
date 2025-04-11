import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../../../components/navbar/navbar.component';

@Component({
  selector: 'app-candidato-page',
  imports: [RouterModule, NavbarComponent],
  templateUrl: './candidato-page.component.html',
  styleUrl: './candidato-page.component.css'
})
export class CandidatoPageComponent {

}
