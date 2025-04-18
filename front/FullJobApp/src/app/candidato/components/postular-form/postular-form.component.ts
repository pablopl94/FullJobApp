import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { VacantesService } from '../../../core/services/vacantes.service';
import { IVacante } from '../../../core/interfaces/IVacante';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-postular-form',
  imports: [CommonModule,
    FormsModule],
  templateUrl: './postular-form.component.html',
  styleUrl: './postular-form.component.css'
})
export class PostularFormComponent {
  idVacante!: number;
  comentarios: string = '';

  constructor(
    private route: ActivatedRoute,
    private vacanteService: VacantesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.idVacante = Number(this.route.snapshot.paramMap.get('id'));
  }

  postular(): void {
    const solicitudDto = {
      comentarios: this.comentarios,
      archivo: 'cv_placeholder.pdf', 
      curriculum: 'http://localhost:8080/cv/cv_placeholder.pdf'
    };

    this.vacanteService.postularme(this.idVacante, solicitudDto).subscribe({
      next: () => {
        alert('Inscrito a la convocatoria con éxito');
        // Redirigir a la página de solicitudes

        this.router.navigate(['/candidato/solicitudes']);
      },
      error: err => {
        console.error(' Error al postularse:', err);
        alert('Ya estás postulado a esta vacante.');
        this.router.navigate(['/candidato/solicitudes']);
      }
    });
  }

  volver(): void {
    this.router.navigate(['/candidato/vacantes']);
  }
}
