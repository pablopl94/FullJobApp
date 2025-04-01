import { Routes } from '@angular/router';
import { empresaGuard } from '../guards/empresa.guard';


export const empresaRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/empresa-page.component').then(m => m.EmpresaPageComponent),
    canActivate: [empresaGuard],
  },
];
