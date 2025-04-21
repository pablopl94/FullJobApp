import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { VacantesService } from '../../../core/services/vacantes.service';
import { IVacante } from '../../../core/interfaces/IVacante';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

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
  curriculum: File | null = null;  
  cargando: boolean = false;    

  constructor(
    private route: ActivatedRoute,
    private vacanteService: VacantesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.idVacante = Number(this.route.snapshot.paramMap.get('id'));
  }

  // Método para manejar la carga del cv
  onFileChange(event: any): void {
    const file = event.target.files[0]; // Obtenemos el archivo seleccionado
    if (file && file.type === 'application/pdf') {
      this.curriculum = file;
      console.log('Archivo seleccionado:', file);
    } else {
      Swal.fire('Formato inválido', 'Solo se permiten archivos PDF', 'warning'); 
      this.curriculum = null;
    }
  }

  // Método para enviar la solicitud
  postular(): void {
    // Verifica que se haya seleccionado un archivo
    if (!this.curriculum) {
      Swal.fire('Archivo requerido', 'Debes cargar un archivo PDF', 'warning'); 
      return;
    }

    const formData = new FormData();
    formData.append('comentarios', this.comentarios);
    formData.append('curriculum', this.curriculum); // Añadir el archivo

    // Log para verificar los datos antes de enviarlos
    console.log('Datos a enviar:', {
      comentarios: this.comentarios,
      archivo: this.curriculum.name,  // Solo el nombre del archivo para ver qué se está enviando
    });

    this.cargando = true; // Activamos el modo "cargando"

    // Llamada al servicio para enviar la solicitud
    this.vacanteService.postularme(this.idVacante, formData).subscribe({
      next: (response) => {
        console.log('Respuesta del servidor:', response);
        this.cargando = false; // Desactivamos "cargando"

        Swal.fire({
          title: '¡Éxito!',
          text: 'Inscrito a la convocatoria con éxito.',
          icon: 'success',
          confirmButtonText: 'Ver mis solicitudes'
        }).then(() => {
          this.router.navigate(['/candidato/solicitudes']);
        });
      },
      error: (err) => {
        console.error('Error al postularse:', err);
        this.cargando = false; 

        Swal.fire({
          title: 'Postulación repetida',
          text: 'Ya estás postulado a esta vacante.',
          icon: 'info',
          confirmButtonText: 'Ver mis solicitudes'
        }).then(() => {
          this.router.navigate(['/candidato/solicitudes']);
        });
      }
    });
  }

  volver(): void {
    this.router.navigate(['/candidato/vacantes']);
  }
}