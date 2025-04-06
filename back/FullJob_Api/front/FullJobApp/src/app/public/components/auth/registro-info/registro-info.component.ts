import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-registro-info',
  imports: [CommonModule,RouterLink], 
  templateUrl: './registro-info.component.html',
  styleUrl: './registro-info.component.css'
})
export class RegistroInfoComponent {

  // Input definiendo que puede ser empresa o candidato
  @Input() tipo: 'empresa' | 'candidato' = 'candidato';

  //Segun el rol mostrara un texto o otro
  get acciones(): { icono: string, titulo: string, texto: string }[] {
    return this.tipo === 'empresa'
      ? [
          {
            icono: '📄',
            titulo: 'Redacta y publica ofertas',
            texto: 'Publica tus ofertas en pocos minutos desde nuestra app'
          },
          {
            icono: '✔️',
            titulo: 'Accede a nuestra base de datos',
            texto: 'Accede a toda la información desde nuestra app'
          },
          {
            icono: '🔄',
            titulo: 'Sigue las candidaturas',
            texto: 'Simple, sencillo, rápido y sin esfuerzos'
          }
        ]
      : [
          {
            icono: '📄',
            titulo: 'Sube tu CV',
            texto: 'Muestra a las empresas todo tu talento'
          },
          {
            icono: '✔️',
            titulo: 'Inscríbete en las ofertas que te interesan',
            texto: 'y sigue tus candidaturas desde nuestra app'
          },
          {
            icono: '🔄',
            titulo: 'Mantén tu CV actualizado',
            texto: 'y doblas las posibilidades de ser contactado'
          }
        ];
  }

  // Texto del botón según el rol
  get textoBoton(): string {
    return this.tipo === 'empresa' ? 'Contactar' : 'Registrarse';
  }

  //Ruta que se usará en el botón según el rol
  get link(): string {
    return this.tipo === 'empresa' ? '/empresa/contacto' : '/registro';
  }
}
