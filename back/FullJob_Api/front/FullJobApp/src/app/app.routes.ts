import { Routes } from '@angular/router';
import { adminRoutes } from './admin/admin.routes';
import { empresaRoutes } from './empresa/empresa.routes';
import { candidatoRoutes } from './candidato/candidato.routes';
import { authRoutes } from './auth/auth.routes';
import { publicRoutes } from './public/public.routes';

export const routes: Routes = [
  { path: 'admin', children: adminRoutes },
  { path: 'empresa', children: empresaRoutes },
  { path: 'candidato', children: candidatoRoutes },
  { path: 'auth', children: authRoutes },

  // 👇 MOVEMOS ESTAS AQUÍ AL NIVEL RAÍZ
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



//   // 🟨 Layout público que envuelve todas las páginas públicas
//   {
//     path: '',
//     component: PublicLayoutComponent,
//     children: [
//       // Landing publica
//       {
//         path: '',
//         loadComponent: () =>
//           import('./public/pages/landing-page/public-landing/public-landing.component')
//             .then(m => m.PublicLandingComponent)
//       },

//       // Acceso a candidatos
//       {
//         path: 'access',
//         loadComponent: () =>
//           import('./public/pages/access-candidato/access-candidato/access-candidato.component')
//             .then(m => m.AccessCandidatoComponent)
//       },

//       // Acceso a empresa
//       {
//         path: 'empresa/access',
//         loadComponent: () =>
//           import('./public/pages/access-empresa/access-empresa/access-empresa.component')
//             .then(m => m.AccessEmpresaComponent)
//       }
//     ]
//   },

//   //Rutas no encontradas
//   {
//     path: '**',
//     redirectTo: ''
//   }
// ];



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



