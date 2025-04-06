import { Routes } from '@angular/router';
import { candidatoGuard } from '../guards/candidato.guard';

export const candidatoRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/candidato-page.component').then(m => m.CandidatoPageComponent),
    canActivate: [candidatoGuard],
  },
];
