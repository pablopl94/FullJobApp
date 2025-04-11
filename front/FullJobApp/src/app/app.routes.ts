import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'admin',
    loadChildren: () =>
      import('./admin/admin.routes').then(m => m.adminRoutes)
  },
  {
    path: 'empresa',
    loadChildren: () =>
      import('./empresa/empresa.routes').then(m => m.empresaRoutes)
  },
  {
    path: 'candidato',
    loadChildren: () =>
      import('./candidato/candidato.routes').then(m => m.candidatoRoutes)
  },

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

  {
    path: '',
    loadComponent: () =>
      import('./public/pages/landing-page/landing-page.component').then(
        m => m.LandingPageComponent
      )
  },

  { path: '**', redirectTo: '' }
];
