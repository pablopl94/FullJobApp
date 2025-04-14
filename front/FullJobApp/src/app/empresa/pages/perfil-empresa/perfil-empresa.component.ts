import { Component, inject } from '@angular/core';
import { EmpresaService } from '../../../core/services/empresa.service';
import { IEmpresa } from '../../../core/interfaces/IEmpresa';

@Component({
  selector: 'app-perfil-empresa',
  imports: [],
  templateUrl: './perfil-empresa.component.html',
  styleUrl: './perfil-empresa.component.css'
})
export class PerfilEmpresaComponent {

  empresa!: IEmpresa;
  empresaServices = inject(EmpresaService);

  constructor() {}

  ngOnInit(): void {
    this.empresaServices.getDetallesEmpresaAutenticada().subscribe({
      next: (data) => {
        this.empresa = data;
      }
    }); 
  }

}