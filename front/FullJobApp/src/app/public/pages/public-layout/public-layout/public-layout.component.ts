import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PublicFooterComponent } from "../../../components/footer-component/public-footer/public-footer.component";
import { PublicNavComponent } from "../../../components/navbar-component/public-nav/public-nav.component";

@Component({
  selector: 'app-public-layout',
  imports: [RouterOutlet, PublicFooterComponent, PublicNavComponent],
  templateUrl: './public-layout.component.html',
  styleUrl: './public-layout.component.css'
})
export class PublicLayoutComponent {

}
