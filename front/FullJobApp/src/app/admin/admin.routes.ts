import { Routes } from '@angular/router';
import { adminGuard } from '../guards/admin.guard';

export const adminRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/admin-page/admin-page.component').then(
        (m) => m.AdminPageComponent
      ),
    canActivate: [adminGuard],
    children: [
      { path: '', redirectTo: 'empresas', pathMatch: 'full' },

      // EMPRESAS
      {
        path: 'empresas',
        loadComponent: () =>
          import('./pages/empresas-page/empresas-page.component').then(
            (m) => m.EmpresasPageComponent
          ),
      },

      // CANDIDATOS
      {
        path: 'candidatos',
        loadComponent: () =>
          import('./pages/candidatos-page/candidatos-page.component').then(
            (m) => m.CandidatosPageComponent
          ),
      },

      // ADMINISTRADORES
      {
        path: 'administradores',
        children: [
          {
            path: '',
            loadComponent: () =>
              import('./pages/administradores-page/administradores-page.component').then(
                (m) => m.AdministradoresPageComponent
              ),
          },
          {
            path: 'alta',
            loadComponent: () =>
              import('./components/administrador-form/administrador-form.component').then(
                (m) => m.AdministradorFormComponent
              ),
          },
          {
            path: 'editar/:email',
            loadComponent: () =>
              import('./components/administrador-form/administrador-form.component').then(
                (m) => m.AdministradorFormComponent
              ),
          },
        ]
      },

      // CATEGORÍAS
      {
        path: 'categorias',
        children: [
          {
            path: '',
            loadComponent: () =>
              import('./pages/categorias-page/categorias-page.component').then(
                (m) => m.CategoriasPageComponent
              ),
          },
          {
            path: 'alta',
            loadComponent: () =>
              import('./components/categoria-form/categoria-form.component').then(
                (m) => m.CategoriaFormComponent
              ),
          },
          {
            path: 'editar/:id',
            loadComponent: () =>
              import('./components/categoria-form/categoria-form.component').then(
                (m) => m.CategoriaFormComponent
              ),
          },
        ]
      },
    ],
  },
];