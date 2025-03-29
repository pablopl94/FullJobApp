import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-login-modal',
  imports: [CommonModule, FormsModule],
  templateUrl: './login-modal.component.html',
  styleUrls: ['./login-modal.component.css']
})
export class LoginModalComponent {
  @Output() close = new EventEmitter<void>();

  email = '';
  password = '';
  error = false;

  private authService: AuthService = inject(AuthService);
private router: Router = inject(Router);


  onLogin() {
    const result = this.authService.login(this.email, this.password);

    if (result) {
      const role = this.authService.getRole();

      switch (role) {
        case 'ADMON':
          this.router.navigate(['/admin']);
          break;
        case 'EMPRESA':
          this.router.navigate(['/empresa']);
          break;
        case 'CANDIDATO':
          this.router.navigate(['/candidato']);
          break;
      }

      this.close.emit();
    } else {
      this.error = true;
    }
  }
}
