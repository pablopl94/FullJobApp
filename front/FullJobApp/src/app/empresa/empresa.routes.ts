import { Routes } from '@angular/router';
import { empresaGuard } from '../guards/empresa.guard';

export const empresaRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/empresa-page/empresa-page.component').then(
        m => m.EmpresaPageComponent
      ),
    canActivate: [empresaGuard],
    children: [
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full',
      },
      {
        path: 'home',
        loadComponent: () =>
          import('./pages/home-page/home-page.component').then(
            m => m.HomePageComponent
          ),
      },
      {
        path: 'vacantes',
        loadComponent: () =>
          import('./pages/vacantes-page/vacantes-page.component').then(
            m => m.VacantesPageComponent
          ),
      },
      {
        path: 'solicitudes',
        loadComponent: () =>
          import('./pages/solicitudes-page/solicitudes-page.component').then(
            m => m.SolicitudesPageComponent
          ),
      },
      {
        path: 'perfil',
        loadComponent: () =>
          import('./pages/perfil-empresa/perfil-empresa.component').then(
            m => m.PerfilEmpresaComponent
          ),
      },
    ]
  }
];
