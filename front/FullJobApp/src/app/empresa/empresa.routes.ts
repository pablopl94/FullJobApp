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
        redirectTo: 'vacantes',
        pathMatch: 'full',
      },
      {
        path: 'vacantes',
        loadComponent: () =>
          import('./pages/vacantes-page/vacantes-page.component').then(
            m => m.VacantesPageComponent
          ),
      },
      // futuras rutas aquí: solicitudes, perfil...
    ]
  }
];

