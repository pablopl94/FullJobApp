import { Component } from '@angular/core';
import { IVacante } from '../../../core/interfaces/IVacante';
import { ActivatedRoute, Router } from '@angular/router';
import { VacantesService } from '../../../core/services/vacantes.service';

@Component({
  selector: 'app-vacante-detail',
  imports: [],
  templateUrl: './vacante-detail.component.html',
  styleUrl: './vacante-detail.component.css'
})
export class VacanteDetailComponent {


  vacante!: IVacante;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private vacanteService: VacantesService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.vacanteService.findById(id).subscribe({
      next: (data) => this.vacante = data,
      error: (err) => console.error('Error al cargar detalles', err)
    });
  }

  volver(): void {
    this.router.navigate(['/candidato/vacantes']);
  }
  inscripcion(): void {
    this.router.navigate(['/candidato/vacantes/postular', this.vacante.idVacante]);
  }

}
