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

  @Input() tipo: 'candidato' | 'empresa' | 'admin' = 'candidato';

  get acciones(): { icono: string, titulo: string, texto: string }[] {
    return this.tipo === 'empresa'
      ? [
          {
            icono: '📄',
            titulo: 'Redacta y publica ofertas',
            texto: 'Publica tus ofertas en pocos minutos '
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

  get textoBoton(): string {
    return this.tipo === 'empresa' ? 'Contactar' : 'Registrarse';
  }

  get link(): string {
    return this.tipo === 'empresa' ? '/empresa/contacto' : '/registro';
  }
}
