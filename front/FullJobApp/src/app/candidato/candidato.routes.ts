import { Routes } from '@angular/router';
import { candidatoGuard } from '../guards/candidato.guard';

export const candidatoRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/candidato-page/candidato-page.component').then(m => m.CandidatoPageComponent),
    canActivate: [candidatoGuard],
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', loadComponent: () => import('./pages/home-page/home-page.component').then(m => m.HomePageComponent) },
      { path: 'vacantes', loadComponent: () => import('./pages/vacantes-page/vacantes-page.component').then(m => m.VacantesPageComponent) },
      { path: 'vacantes/:id', loadComponent: () => import('./pages/vacante-detail/vacante-detail.component').then(m => m.VacanteDetailComponent) },
      { path: 'vacantes/postular/:id', loadComponent: () => import('./components/postular-form/postular-form.component').then(m => m.PostularFormComponent) },
      { path: 'solicitudes', loadComponent: () => import('./pages/solicitudes-page/solicitudes-page.component').then(m => m.SolicitudesPageComponent) },
      { path: 'solicitudes/:id', loadComponent: () => import('./pages/solicitud-detail/solicitud-detail.component').then(m => m.SolicitudDetailComponent) }
    ]
  }
];


