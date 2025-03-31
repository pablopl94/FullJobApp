import { Routes } from '@angular/router';
import { AccessEmpresaComponent } from './public/pages/access-empresa/access-empresa/access-empresa.component';
import { AccessCandidatoComponent } from './public/pages/access-candidato/access-candidato/access-candidato.component';
import { PublicLandingComponent } from './public/pages/landing-page/public-landing/public-landing.component';
import { PublicLayoutComponent } from './public/pages/public-layout/public-layout/public-layout.component';


export const routes: Routes = [
  // 🟨 Layout público que envuelve todas las páginas públicas
  {
    path: '',
    component: PublicLayoutComponent,
    children: [
      // Landing publica
      {
        path: '',
        loadComponent: () =>
          import('./public/pages/landing-page/public-landing/public-landing.component')
            .then(m => m.PublicLandingComponent)
      },

      // Acceso a candidatos
      {
        path: 'access',
        loadComponent: () =>
          import('./public/pages/access-candidato/access-candidato/access-candidato.component')
            .then(m => m.AccessCandidatoComponent)
      },

      // Acceso a empresa
      {
        path: 'empresa/access',
        loadComponent: () =>
          import('./public/pages/access-empresa/access-empresa/access-empresa.component')
            .then(m => m.AccessEmpresaComponent)
      }
    ]
  },

  //Rutas no encontradas
  {
    path: '**',
    redirectTo: ''
  }
];



//   // Página pública (Landing)
//   {
//     path: '',
//     loadComponent: () =>
//       import('./pages/landing-page/landing-page.component').then(m => m.LandingPageComponent)
//   },

//   // Zona Admin
//   {
//     path: 'admin',
//     loadComponent: () =>
//       import('./pages/admin-page/admin-page.component').then(m => m.default)
//   },

//   // Zona Empresa
//   {
//     path: 'empresa',
//     loadComponent: () =>
//       import('./pages/empresa-page/empresa-page.component').then(m => m.default)
//   },

//   // Zona Candidato
//   {
//     path: 'candidato',
//     loadComponent: () =>
//       import('./pages/candidato-page/candidato-page.component').then(m => m.default)
//   },

//   // Ruta no encontrada
//   {
//     path: '**',
//     redirectTo: ''
//   }
// ];



