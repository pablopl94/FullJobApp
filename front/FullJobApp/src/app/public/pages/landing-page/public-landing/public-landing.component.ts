import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-public-landing',
  imports: [RouterLink],
  templateUrl: './public-landing.component.html',
  styleUrl: './public-landing.component.css'
})
export class PublicLandingComponent {

}
