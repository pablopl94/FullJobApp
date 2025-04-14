import { Component, inject, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { VacantesService } from '../../../core/services/vacantes.service';
import { BotonesVacanteEmpresaComponent } from '../../components/botones-vacante-empresa/botones-vacante-empresa.component';
import { RouterLink } from '@angular/router';
import { IVacante } from '../../../core/interfaces/IVacante';


@Component({
  standalone: true,
  selector: 'app-vacantes-page',
  templateUrl: './vacantes-page.component.html',
  styleUrls: ['./vacantes-page.component.css'],
  imports: [CommonModule, BotonesVacanteEmpresaComponent,RouterLink],
})

export class VacantesPageComponent {

  @Input() vacanteId!: number; 
  authService = inject(AuthService);
  VacantesService = inject(VacantesService);

  arrayVacantes: IVacante[] = [];

  constructor() {}

  ngOnInit(): void {
    //Cargamos las vacantes la primera vez
    this.VacantesService.cargarVacantes();
  
    //Nos suscribimos al observable para recibir actualizaciones automáticas
    this.VacantesService.vacantes$.subscribe({
      next: (vacantes) => this.arrayVacantes = vacantes,
      error: (err) => console.error('Error al recibir las vacantes:', err)
    });
  }

}




