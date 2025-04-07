import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmpresaService } from '../../../core/services/empresa.service';
import { Vacante } from '../../../core/interfaces/vacante';


@Component({
  standalone: true,
  selector: 'app-vacantes-page',
  templateUrl: './vacantes-page.component.html',
  styleUrls: ['./vacantes-page.component.css'],
  imports: [CommonModule],
})

export class VacantesPageComponent {
  vacantes: Vacante[] = [];

  constructor(private empresaService: EmpresaService) {}


}
