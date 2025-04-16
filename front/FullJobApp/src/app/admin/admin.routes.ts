import { Routes } from '@angular/router';

export const adminRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/admin-page/admin-page.component').then(
        (m) => m.AdminPageComponent
      ),
    children: [
      { path: '', redirectTo: 'empresas', pathMatch: 'full' },
      {
        path: 'empresas',
        loadComponent: () =>
          import('./pages/empresas-page/empresas-page.component').then(
            (m) => m.EmpresasPageComponent
          ),
      },
      {
        path: 'candidatos',
        loadComponent: () =>
          import('./pages/candidatos-page/candidatos-page.component').then(
            (m) => m.CandidatosPageComponent
          ),
      },
      {
        path: 'administradores',
        loadComponent: () =>
          import(
            './pages/administradores-page/administradores-page.component'
          ).then((m) => m.AdministradoresPageComponent),
      },

      {
        path: 'administradores',
        loadComponent: () =>
          import('./pages/administradores-page/administradores-page.component').then(
            (m) => m.AdministradoresPageComponent
          ),
        children: [
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
        ],
      },

      {
        path: 'categorias',
        loadComponent: () =>
          import('./pages/categorias-page/categorias-page.component').then(
            (m) => m.CategoriasPageComponent
          ),
      },

      {
        path: 'categorias',
        loadComponent: () =>
          import('./pages/categorias-page/categorias-page.component').then(
            (m) => m.CategoriasPageComponent
          ),
        children: [
          {
            path: 'alta',
            loadComponent: () =>
              import(
                './components/categoria-form/categoria-form.component'
              ).then((m) => m.CategoriaFormComponent),
          },
          {
            path: 'editar/:id',
            loadComponent: () =>
              import(
                './components/categoria-form/categoria-form.component'
              ).then((m) => m.CategoriaFormComponent),
          },
        ],
      },
    ],
  },
];







// import { Routes } from '@angular/router';

// export const adminRoutes: Routes = [
//   {
//     path: '',
//     loadComponent: () =>
//       import('./pages/admin-page/admin-page.component').then(
//         (m) => m.AdminPageComponent
//       ),
//     children: [
//       { path: '', redirectTo: 'empresas', pathMatch: 'full' },

//       // EMPRESAS
//       {
//         path: 'empresas',
//         loadComponent: () =>
//           import('./pages/empresas-page/empresas-page.component').then(
//             (m) => m.EmpresasPageComponent
//           ),
//         children: [
//           {
//             path: 'alta',
//             loadComponent: () =>
//               import('./components/empresa-form/empresa-form.component').then(
//                 (m) => m.EmpresaFormComponent
//               ),
//           },
//           {
//             path: 'editar/:id',
//             loadComponent: () =>
//               import('./components/empresa-form/empresa-form.component').then(
//                 (m) => m.EmpresaFormComponent
//               ),
//           },
//         ],
//       },

//       // CANDIDATOS
//       {
//         path: 'candidatos',
//         loadComponent: () =>
//           import('./pages/candidatos-page/candidatos-page.component').then(
//             (m) => m.CandidatosPageComponent
//           ),
//       },

//       // ADMINISTRADORES
//       {
//         path: 'administradores',
//         loadComponent: () =>
//           import(
//             './pages/administradores-page/administradores-page.component'
//           ).then((m) => m.AdministradoresPageComponent),
//         children: [
//           {
//             path: 'alta',
//             loadComponent: () =>
//               import('./components/administrador-form/administrador-form.component').then(
//                 (m) => m.AdministradorFormComponent
//               ),
//           },
//           {
//             path: 'editar/:email',
//             loadComponent: () =>
//               import('./components/administrador-form/administrador-form.component').then(
//                 (m) => m.AdministradorFormComponent
//               ),
//           },
//         ],
//       },

//       // CATEGORÍAS
//       {
//         path: 'categorias',
//         loadComponent: () =>
//           import('./pages/categorias-page/categorias-page.component').then(
//             (m) => m.CategoriasPageComponent
//           ),
//         children: [
//           {
//             path: 'alta',
//             loadComponent: () =>
//               import('./components/categoria-form/categoria-form.component').then(
//                 (m) => m.CategoriaFormComponent
//               ),
//           },
//           {
//             path: 'editar/:id',
//             loadComponent: () =>
//               import('./components/categoria-form/categoria-form.component').then(
//                 (m) => m.CategoriaFormComponent
//               ),
//           },
//         ],
//       },
//     ],
//   },
// ];
