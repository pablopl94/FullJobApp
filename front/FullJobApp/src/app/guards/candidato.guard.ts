import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../core/services/auth.service';

export const candidatoGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.estaLogueado() && authService.obtenerRol() === 'CLIENTE') return true;

  router.navigate(['/']);
  return false;
};

