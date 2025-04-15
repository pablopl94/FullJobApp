import { Component } from '@angular/core';
import { PublicNavComponent } from '../../components/public-nav/public-nav.component';
import { FooterComponent } from "../../../components/footer/footer.component";
import { RouterModule } from '@angular/router';



@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [PublicNavComponent, FooterComponent, RouterModule],
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent {

}
