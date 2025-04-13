import { Routes } from '@angular/router';
import { adminGuard } from '../guards/admin.guard';
import { EmpresaFormComponent } from './components/empresa-form/empresa-form.component';

export const adminRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/admin-page/admin-page.component').then(m => m.AdminPageComponent),    
    children: [
      { path: '',redirectTo: 'empresas',pathMatch: 'full',},
      {
        path: 'empresas',
        loadComponent: () =>
          import('./pages/empresas-page/empresas-page.component').then(m => m.EmpresasPageComponent)
      },
      {
        path: 'candidatos',
        loadComponent: () =>
          import('./pages/candidatos-page/candidatos-page.component').then(m => m.CandidatosPageComponent)
      },
      {
        path: 'administradores',
        loadComponent: () =>
          import('./pages/administradores-page/administradores-page.component').then(m => m.AdministradoresPageComponent)
      },
      {
        path: 'categorias',
        loadComponent: () =>
          import('./pages/categorias-page/categorias-page.component').then(m => m.CategoriasPageComponent)
      }

      // //Ejemplo de ruta hija
      // path: 'empresa',
      // children: [
      //   {
      //     path: '',
      //     loadComponent: () => import('./pages/empresas-page/empresas-page.component').then(m => m.EmpresasPageComponent)
      //   },
      //   {
      //     path: 'alta',
      //     loadComponent: () => import('./pages/solicitud-details/solicitud-details.component').then(m => m.EmpresaFormComponent),
      //   }
      // ]

    ]
  }
];

