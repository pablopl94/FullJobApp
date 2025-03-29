import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginModalComponent } from '../../auth/login-modal/login-modal.component';


@Component({
  standalone: true,
  selector: 'app-landing-page',
  imports: [CommonModule, LoginModalComponent],
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent {
  showLogin = false;
}
