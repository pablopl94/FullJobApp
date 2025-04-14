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
      { path: '',redirectTo: 'home',pathMatch: 'full',},
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
        path: 'vacantes',
        children: [
          {
            path: '',
            loadComponent: () =>
              import('./pages/vacantes-page/vacantes-page.component').then(m => m.VacantesPageComponent),
          },
          {
            path: 'alta',
            loadComponent: () => import('./pages/vacante-form/vacante-form.component').then(m => m. VacanteFormComponent),
          },
          {
            path: 'editar/:idVacante',
            loadComponent: () => import('./pages/vacante-form/vacante-form.component').then(m => m. VacanteFormComponent),
          }
        ]
      },
      {
        path: 'solicitudes',
        children: [
          {
            path: '',
            loadComponent: () => import('./pages/solicitudes-page/solicitudes-page.component').then(m => m.SolicitudesPageComponent),
          },
          {
            path: 'detalle/:id',
            loadComponent: () => import('./pages/solicitud-details/solicitud-details.component').then(m => m.SolicitudDetailsComponent),
          }
        ]
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
