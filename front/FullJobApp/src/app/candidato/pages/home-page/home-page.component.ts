import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { RouterLink } from '@angular/router';
import { VacantesService } from '../../../core/services/vacantes.service';
import { IVacante } from '../../../core/interfaces/IVacante';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  private readonly vacantesService = inject(VacantesService);
  private readonly authService = inject(AuthService);

  usuario: any = null;
  recientes: IVacante[] = [];
  populares: IVacante[] = [];

  ngOnInit(): void {
    
    // Cargamos usuario logueado (si existe)
    this.usuario = this.authService.obtenerUsuario();
  
    // Llamamos al método para cargar las vacantes públicas
    this.vacantesService.cargarTodasLasVacantesActivas(); // << FALTABA ESTO
  
    // Nos suscribimos al observable para recibir las vacantes
    this.vacantesService.vacantesPublicas$.subscribe(vacantes => {
      if (!vacantes || vacantes.length === 0) {
        console.warn('No hay vacantes disponibles');
      }
  
      // Filtramos por las que están en estado CREADA
      const creadas = vacantes.filter(v => v.estatus === 'CREADA');
  
      // Tomamos las destacadas (destacado = 1)
      this.populares = creadas.filter(v => v.destacado === 1).slice(0, 5);
  
      // Ordenamos por fecha descendente
      this.recientes = [...creadas]
        .sort((a, b) => b.fecha.localeCompare(a.fecha))
        .slice(0, 5);
    });
  }
}