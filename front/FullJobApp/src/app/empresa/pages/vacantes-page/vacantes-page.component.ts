import { Component, inject, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Vacante } from '../../../core/interfaces/vacante';
import { IUsuario } from '../../../core/interfaces/iusuario';
import { AuthService } from '../../../core/services/auth.service';
import { VacantesService } from '../../../core/services/vacantes.service';
import { VacanteCardComponent } from '../../components/vacante-card/vacante-card.component';
import { BotonesVacanteEmpresaComponent } from '../../components/botones-vacante-empresa/botones-vacante-empresa.component';


@Component({
  standalone: true,
  selector: 'app-vacantes-page',
  templateUrl: './vacantes-page.component.html',
  styleUrls: ['./vacantes-page.component.css'],
  imports: [CommonModule],
})

export class VacantesPageComponent {
  @Input() vacanteId!: number; 
  arrayVacantes: Vacante[] = [];
  usuario !: IUsuario;
  authService = inject(AuthService);
  VacantesService = inject(VacantesService);

  constructor() {}

  ngOnInit() {
    this.usuario = this.authService.obtenerUsuario();
    
    this.VacantesService.getMisVacantes().subscribe((vacantes: Vacante[]) => {
      console.log('Vacantes recibidas:', vacantes);
      this.arrayVacantes = vacantes;
    }
    );
  }


}
