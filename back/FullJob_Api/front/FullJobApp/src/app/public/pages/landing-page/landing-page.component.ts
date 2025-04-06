import { Component } from '@angular/core';
import { PublicNavComponent } from '../../components/public-nav/public-nav.component';



@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [PublicNavComponent],
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent {

}
