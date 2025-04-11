import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmpresaService } from '../../../core/services/empresa.service';
import { AuthService } from '../../../core/services/auth.service';
import { Vacante } from '../../../core/interfaces/vacante';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  private readonly empresaService = inject(EmpresaService);
  private readonly authService = inject(AuthService);

  usuario: any = null;
  recientes: Vacante[] = [];
  populares: Vacante[] = [];

  ngOnInit(): void {
    this.usuario = this.authService.obtenerUsuario();

    this.empresaService.getVacantes().subscribe(vacantes => {
      const creadas = vacantes.filter(v => v.estatus === 'CREADA');
      this.populares = creadas.filter(v => v.destacado).slice(0, 5);
      this.recientes = [...creadas].sort((a, b) => b.fecha.localeCompare(a.fecha)).slice(0, 5);
    });
  }

}

