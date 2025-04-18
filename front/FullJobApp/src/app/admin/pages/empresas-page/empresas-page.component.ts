import { Component, OnInit } from '@angular/core';
import { EmpresaService } from '../../../core/services/empresa.service';
import { IEmpresa } from '../../../core/interfaces/IEmpresa';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-empresas-page',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './empresas-page.component.html',
  styleUrl: './empresas-page.component.css',
})
export class EmpresasPageComponent implements OnInit {


  //EN ESTE FORMULARIO HEMOS QUERIDO PROBAR UN FORMULARIO CON SWEET ALERT

  // Lista de empresas que se mostrará en la tabla
  empresas: IEmpresa[] = [];

  constructor(private empresaService: EmpresaService) {}

  ngOnInit(): void {
    // Cargar todas las empresas al iniciar el componente
    this.empresaService.fetchEmpresas();

    // Nos suscribimos al observable para obtener las empresas
    this.empresaService.empresas$.subscribe({
      next: (empresas) => {
        console.log(empresas);
        this.empresas = empresas;
      },
      error: (err) => {
        console.error('Error al recibir empresas', err);
      },
    });
  }

  // Validar los campos obligatorios del formulario
  validarCampos(nombre: string, cif: string, pais: string, email: string): string | null {
    if (!nombre || !cif || !pais || !email) {
      return 'Todos los campos son obligatorios.';
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      return 'El correo electrónico no es válido.';
    }

    return null;
  }

  // Formulario para crear nueva empresa con SweetAlert
  async nuevaEmpresa() {
    const { value: formValues } = await Swal.fire({
      title: 'Nueva Empresa',
      html: `
        <input id="nombreEmpresa" class="swal2-input" placeholder="Nombre empresa">
        <input id="cif" class="swal2-input" placeholder="CIF">
        <input id="pais" class="swal2-input" placeholder="País">
        <input id="direccionFiscal" class="swal2-input" placeholder="Dirección Fiscal">
        <input id="email" class="swal2-input" placeholder="Correo electrónico">
        <input id="nombre" class="swal2-input" placeholder="Nombre usuario">
        <input id="apellidos" class="swal2-input" placeholder="Apellidos usuario">
      `,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Guardar',
      preConfirm: () => {
        const nombreEmpresa = (document.getElementById('nombreEmpresa') as HTMLInputElement).value.trim();
        const cif = (document.getElementById('cif') as HTMLInputElement).value.trim();
        const pais = (document.getElementById('pais') as HTMLInputElement).value.trim();
        const direccionFiscal = (document.getElementById('direccionFiscal') as HTMLInputElement).value.trim();
        const email = (document.getElementById('email') as HTMLInputElement).value.trim();
        const nombre = (document.getElementById('nombre') as HTMLInputElement).value.trim();
        const apellidos = (document.getElementById('apellidos') as HTMLInputElement).value.trim();

        const error = this.validarCampos(nombreEmpresa, cif, pais, email);
        if (error) {
          Swal.showValidationMessage(error);
          return;
        }

        return {
          nombreEmpresa,
          cif,
          pais,
          direccionFiscal,
          email,
          nombre,
          apellidos,
          password: null, // Se autogenera en el backend
        };
      },
    });

    // Enviar los datos al backend si todo es válido
    if (formValues) {
      const nuevaEmpresa: IEmpresa = formValues;

      this.empresaService.crearEmpresa(nuevaEmpresa).subscribe({
        next: () => {
          Swal.fire('Creada', 'La empresa ha sido creada', 'success');
        },
        error: (err) => {
          console.error('Error al crear empresa', err);
          Swal.fire('Error', 'No se pudo crear la empresa', 'error');
        },
      });
    }
  }

  // Formulario para editar una empresa existente
  async editarEmpresa(empresa: IEmpresa) {
    const { value: formValues } = await Swal.fire({
      title: 'Modificar Empresa',
      html: `
        <input id="nombreEmpresa" class="swal2-input" placeholder="Nombre empresa" value="${empresa.nombreEmpresa}">
        <input id="cif" class="swal2-input" placeholder="CIF" value="${empresa.cif}">
        <input id="pais" class="swal2-input" placeholder="País" value="${empresa.pais}">
        <input id="direccionFiscal" class="swal2-input" placeholder="Dirección Fiscal" value="${empresa.direccionFiscal}">
        <input id="email" class="swal2-input" placeholder="Correo electrónico" value="${empresa.email || ''}">
      `,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Actualizar',
      preConfirm: () => {
        const nombreEmpresa = (document.getElementById('nombreEmpresa') as HTMLInputElement).value.trim();
        const cif = (document.getElementById('cif') as HTMLInputElement).value.trim();
        const pais = (document.getElementById('pais') as HTMLInputElement).value.trim();
        const direccionFiscal = (document.getElementById('direccionFiscal') as HTMLInputElement).value.trim();
        const email = (document.getElementById('email') as HTMLInputElement).value.trim();

        const error = this.validarCampos(nombreEmpresa, cif, pais, email);
        if (error) {
          Swal.showValidationMessage(error);
          return;
        }

        return { nombreEmpresa, cif, pais, direccionFiscal, email };
      },
    });

    // Si los datos son válidos, actualizamos la empresa
    if (formValues) {
      const actualizada: IEmpresa = {
        ...empresa,
        ...formValues,
      };

      this.empresaService.actualizarEmpresa(actualizada).subscribe({
        next: () => {
          Swal.fire('Actualizada', 'La empresa ha sido actualizada', 'success');
        },
        error: (err) => {
          console.error('Error al actualizar empresa', err);
          Swal.fire('Error', 'No se pudo actualizar la empresa', 'error');
        },
      });
    }
  }

  // Mostrar detalles de la empresa seleccionada
  mostrarDetalles(empresa: IEmpresa) {
    Swal.fire({
      title: 'Detalles de Empresa',
      html: `
        <p><b>ID:</b> ${empresa.idEmpresa}</p>
        <p><b>Nombre:</b> ${empresa.nombreEmpresa}</p>
        <p><b>CIF:</b> ${empresa.cif}</p>
        <p><b>País:</b> ${empresa.pais}</p>
        <p><b>Dirección:</b> ${empresa.direccionFiscal}</p>
        <p><b>Email:</b> ${empresa.email}</p>
        <p><b>Fecha Registro:</b> ${empresa.fechaRegistro}</p>
      `,
    });
  }

  // Confirmar y eliminar una empresa
  eliminarEmpresa(id: number) {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará la empresa permanentemente',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.empresaService.eliminarEmpresa(id).subscribe({
          next: () => {
            Swal.fire('Eliminada', 'La empresa ha sido eliminada', 'success');
          },
          error: (err) => {
            console.error('Error al eliminar empresa', err);
            Swal.fire('Error', 'No se pudo eliminar la empresa', 'error');
          },
        });
      }
    });
  }
}