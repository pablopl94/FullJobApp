import { Routes } from '@angular/router';
import { adminRoutes } from './admin/admin.routes';
import { empresaRoutes } from './empresa/empresa.routes';
import { candidatoRoutes } from './candidato/candidato.routes';
import { publicRoutes } from './public/public.routes';

export const routes: Routes = [
  { path: 'admin', children: adminRoutes },
  { path: 'empresa', children: empresaRoutes },
  { path: 'candidato', children: candidatoRoutes },

  {
    path: 'access/candidato',
    loadComponent: () =>
      import('./public/pages/access-candidato/access-candidato.component').then(
        m => m.AccessCandidatoComponent
      )
  },
  {
    path: 'access/empresa',
    loadComponent: () =>
      import('./public/pages/access-empresa/access-empresa.component').then(
        m => m.AccessEmpresaComponent
      )
  },

  { path: '', loadComponent: () =>
      import('./public/pages/landing-page/landing-page.component').then(
        m => m.LandingPageComponent
      )
  },

  { path: '**', redirectTo: '' }
];
