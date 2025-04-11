
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from "../../../components/navbar/navbar.component";


@Component({
  standalone: true,
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css'],
  imports: [RouterModule, NavbarComponent]
})
export class AdminPageComponent {

}
