import { Routes } from '@angular/router';
import { adminGuard } from '../guards/admin.guard';

export const adminRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/admin-page.component').then(m => m.AdminPageComponent),
    canActivate: [adminGuard],
  },
];
