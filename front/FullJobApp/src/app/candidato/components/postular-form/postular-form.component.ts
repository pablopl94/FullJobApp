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
  curriculum: File | null = null;  // Variable para almacenar el archivo seleccionado

  constructor(
    private route: ActivatedRoute,
    private vacanteService: VacantesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.idVacante = Number(this.route.snapshot.paramMap.get('id'));
  }

  postular(): void {
    // Verifica que se haya seleccionado un archivo
    if (!this.curriculum) {
      alert('Debes cargar un archivo PDF');
      return;
    }

    const formData = new FormData();
    formData.append('comentarios', this.comentarios);
    formData.append('curriculum', this.curriculum); // Añadir el archivo

    this.vacanteService.postularme(this.idVacante, formData).subscribe({
      next: () => {
        alert('Inscrito a la convocatoria con éxito');
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