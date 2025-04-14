import { Component, inject, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { VacantesService } from '../../../core/services/vacantes.service';
import { RouterLink } from '@angular/router';
import { IVacante } from '../../../core/interfaces/IVacante';
import { VacanteCardComponent } from "../../components/vacante-card/vacante-card.component";


@Component({
  standalone: true,
  selector: 'app-vacantes-page',
  templateUrl: './vacantes-page.component.html',
  styleUrls: ['./vacantes-page.component.css'],
  imports: [VacanteCardComponent, RouterLink] 
})
export class VacantesPageComponent {

  authService = inject(AuthService);
  vacantesService = inject(VacantesService);

  arrayVacantesCreadas: IVacante[] = [];
  arrayVacantesAsginadas: IVacante[] = [];
  arrayVacantesCanceladas: IVacante[] = [];

  ngOnInit(): void {
    
    // Cargamos vacantes creadas
    this.vacantesService.cargarVacantes();
    this.vacantesService.vacantes$.subscribe({
      next: (vacantes) => this.arrayVacantesCreadas = vacantes,
      error: (err) => console.error('Error al recibir vacantes creadas:', err)
    });

    // Cargamos vacantes asignadas
    this.vacantesService.cargarVacantesAsignadas();
    this.vacantesService.vacantesAsignadas$.subscribe({
      next: (vacantes) => this.arrayVacantesAsginadas = vacantes,
      error: (err) => console.error('Error al recibir vacantes asignadas:', err)
    });

    // Cargamos vacantes canceladas (¡CAMBIADO para usar Subject!)
    this.vacantesService.cargarVacantesCanceladas();
    this.vacantesService.vacantesCanceladas$.subscribe({
      next: (vacantes) => this.arrayVacantesCanceladas = vacantes,
      error: (err) => console.error('Error al recibir vacantes canceladas:', err)
    });
  }
}