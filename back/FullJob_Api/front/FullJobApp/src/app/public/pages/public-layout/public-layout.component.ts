import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FooterComponent } from '../../../components/footer/footer.component';
import { PublicNavComponent } from '../../components/public-nav/public-nav.component';


@Component({
  selector: 'app-public-layout',
  imports: [RouterOutlet, FooterComponent, PublicNavComponent],
  templateUrl: './public-layout.component.html',
  styleUrl: './public-layout.component.css'
})
export class PublicLayoutComponent {

}
