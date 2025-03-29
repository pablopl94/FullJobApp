import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module')
  },
  {
    path: 'admin',
    loadChildren: () => import('./pages/admin-page/admin-page.component')
  },
  {
    path: 'empresa',
    loadChildren: () => import('./pages/empresa-page/empresa-page.component')
  },
  {
    path: 'candidato',
    loadChildren: () => import('./pages/candidato-page/candidato-page.component')
  },
  {
    path: '**',
    redirectTo: 'auth/login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
