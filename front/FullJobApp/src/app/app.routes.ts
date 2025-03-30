import { Routes } from '@angular/router';

export const routes: Routes = [

  // Página pública (Landing)
  {
    path: '',
    loadComponent: () =>
      import('./pages/landing-page/landing-page.component').then(m => m.LandingPageComponent)
  },

  // Zona Admin
  {
    path: 'admin',
    loadComponent: () =>
      import('./pages/admin-page/admin-page.component').then(m => m.default)
  },

  // Zona Empresa
  {
    path: 'empresa',
    loadComponent: () =>
      import('./pages/empresa-page/empresa-page.component').then(m => m.default)
  },

  // Zona Candidato
  {
    path: 'candidato',
    loadComponent: () =>
      import('./pages/candidato-page/candidato-page.component').then(m => m.default)
  },

  // Ruta no encontrada
  {
    path: '**',
    redirectTo: ''
  }
];




// import { Routes } from '@angular/router';

// //GUARDS
// import { authGuard } from './guards/auth.guard';
// import { candidatoGuard } from './guards/candidato.guard';
// import { empresaGuard } from './guards/empresa.guard';
// import { adminGuard } from './guards/admin.guard';

// // PÚBLICAS
// import { LandingPageComponent } from './public/pages/landing/landing-page/landing-page.component';
// import { CandidatoLoginComponent } from './public/pages/candidato-access/candidato-login/candidato-login.component';
// import { CandidatoRegisterFormComponent } from './public/pages/candidato-access/candidato-register-form/candidato-register-form.component';
// import { EmpresaLoginComponent } from './public/pages/empresa-access/empresa-login/empresa-login.component';
// import { AdminLoginComponent } from './public/pages/admin-access/admin-login/admin-login.component';

// // CANDIDATO
// import { CandidatoLayoutComponent } from './internal/layout/candidato-layout/candidato-layout.component';
// import { CandidatoHomeComponent } from './internal/pages/candidato/candidato-home/candidato-home/candidato-home.component';
// import { ListaVacantesCandidatoComponent } from './internal/pages/candidato/vacantes/lista-vacantes-candidato/lista-vacantes-candidato.component';
// import { DetallesVacanteCandidatoComponent } from './internal/pages/candidato/vacantes/detalles-vacante-candidato/detalles-vacante-candidato.component';
// import { FormularioInscripcionVacanteComponent } from './internal/pages/candidato/vacantes/formulario-inscripcion-vacante/formulario-inscripcion-vacante.component';
// import { CandidatoSolicitudesComponent } from './internal/pages/candidato/solicitudes/candidato-solicitudes/candidato-solicitudes.component';
// import { CandidatoPerfilComponent } from './internal/pages/candidato/perfil/candidato-perfil/candidato-perfil.component';

// // EMPRESA
// import { EmpresaLayoutComponent } from './internal/layout/empresa-layout/empresa-layout.component';
// import { EmpresaHomeComponent } from './internal/pages/empresa/empresa-home/empresa-home/empresa-home.component';
// import { ListaVacantesEmpresaComponent } from './internal/pages/empresa/mis-vacantes/lista-vacantes-empresa/lista-vacantes-empresa.component';
// import { FormularioVacanteComponent } from './internal/pages/empresa/mis-vacantes/formulario-vacante/formulario-vacante.component';
// import { DetallesVacanteEmpresaComponent } from './internal/pages/empresa/mis-vacantes/detalles-vacante-empresa/detalles-vacante-empresa.component';
// import { ListaSolicitudesComponent } from './internal/pages/empresa/solicitudes/lista-solicitudes/lista-solicitudes.component';
// import { FormularioSolicitudEmpresaComponent } from './internal/pages/empresa/solicitudes/formulario-solicitud-empresa/formulario-solicitud-empresa.component';
// import { EmpresaPerfilComponent } from './internal/pages/empresa/perfil/empresa-perfil/empresa-perfil.component';

// // ADMIN
// import { AdminLayoutComponent } from './internal/layout/admin-layout/admin-layout.component';
// import { ListaEmpresasComponent } from './internal/pages/admin/gestion-empresas/lista-empresas/lista-empresas.component';
// import { FormularioEmpresaComponent } from './internal/pages/admin/gestion-empresas/formulario-empresa/formulario-empresa.component';
// import { ListaCandidatosComponent } from './internal/pages/admin/gestion-candidatos/lista-candidatos/lista-candidatos.component';
// import { ListaAdministradoresComponent } from './internal/pages/admin/gestion-admin/lista-administradores/lista-administradores.component';
// import { FormularioAdminComponent } from './internal/pages/admin/gestion-admin/formulario-admin/formulario-admin.component';
// import { ListaCategoriasComponent } from './internal/pages/admin/gestion-categorias/lista-categorias/lista-categorias.component';
// import { FormularioCategoriaComponent } from './internal/pages/admin/gestion-categorias/formulario-categoria/formulario-categoria.component';


// export const routes: Routes = [

//   // PÚBLICAS
//   { path: '', pathMatch: 'full', redirectTo: 'landing' },
//   { path: 'landing', component: LandingPageComponent },
//   { path: 'candidato', component: CandidatoLoginComponent },
//   { path: 'candidato/editar', component: CandidatoRegisterFormComponent },
//   { path: 'empresa', component: EmpresaLoginComponent },
//   { path: 'admin', component: AdminLoginComponent },

//   // CANDIDATO RUTAS
//   {
//     path: 'candidato-app',
//     component: CandidatoLayoutComponent,
//     canActivate: [authGuard, candidatoGuard],
//     children: [
//       { path: '', pathMatch: 'full', redirectTo: 'home' },
//       { path: 'home', component: CandidatoHomeComponent }, // Página de Home - Zona candidato
//       { path: 'vacantes', component: ListaVacantesCandidatoComponent }, // Pagina de buscar vancantes del candidato
//       { path: 'vacantes/:id', component: DetallesVacanteCandidatoComponent },// Detalles de la vacante
//       { path: 'vacantes/:id/inscripcion', component: FormularioInscripcionVacanteComponent },//Formulario de inscripción a la vacante
//       { path: 'solicitudes', component: CandidatoSolicitudesComponent },//Página con lista de solicitudes del candidato
//       { path: 'perfil', component: CandidatoPerfilComponent } // Página de perfil del candidato
//     ]
//   },

//   // EMPRESA RUTAS
//   {
//     path: 'empresa-app',
//     component: EmpresaLayoutComponent,
//     canActivate: [authGuard, empresaGuard],
//     children: [
//       { path: '', pathMatch: 'full', redirectTo: 'home' },
//       { path: 'home', component: EmpresaHomeComponent }, // Página de Home - Zona empresa
//       { path: 'vacantes', component: ListaVacantesEmpresaComponent }, // Página de vancantes de la empresa
//       { path: 'vacantes/alta', component: FormularioVacanteComponent },//Página formulario de alta de vacante
//       { path: 'vacantes/editar/:id', component: FormularioVacanteComponent },//Página formulario de edición de vacante
//       { path: 'vacantes/:id', component: DetallesVacanteEmpresaComponent },//Página con detalles de la vacante
//       { path: 'solicitudes', component: ListaSolicitudesComponent },//Página con lista de solicitudes de la empresa
//       { path: 'solicitud/alta', component: FormularioSolicitudEmpresaComponent },//Página formulario de alta de solicitud
//       { path: 'solicitud/modificar/:id', component: FormularioSolicitudEmpresaComponent },//Página formulario de modificación de solicitud
//       { path: 'perfil', component: EmpresaPerfilComponent }//Página de perfil de la empresa
//     ]
//   },

//   // ADMINISTRADOR RUTAS
//   {
//     path: 'admin-app',
//     component: AdminLayoutComponent,
//     canActivate: [authGuard, adminGuard],
//     children: [
//       { path: '', pathMatch: 'full', redirectTo: 'empresas' },
//       { path: 'empresas', component: ListaEmpresasComponent },//Página con lista de empresas
//       { path: 'empresas/formulario', component: FormularioEmpresaComponent },//Página formulario de alta de empresa
//       { path: 'candidatos', component: ListaCandidatosComponent },//Página con lista de candidatos
//       { path: 'administradores', component: ListaAdministradoresComponent },//Página con lista de administradores
//       { path: 'administradores/formulario', component: FormularioAdminComponent },//Página formulario de alta de administrador
//       { path: 'categorias', component: ListaCategoriasComponent },//Página con lista de categorías
//       { path: 'categorias/formulario', component: FormularioCategoriaComponent }//Página formulario de alta de categoría
//     ]
//   },

//   // NOT FOUND
//   { path: '**', redirectTo: '' }
// ];
