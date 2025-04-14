export interface ISolicitud {

  //Datos de la solicitud
  idSolicitud: number;
  fecha: string;
  archivo: string;
  curriculum: string;
  comentarios: string;
  salario: number;
  estado: string;
  nombreEmpresa: string;
  
  //Datos de la vacante
  idVacante: number;
  nombreVacante: string;

  //Datos del usuario
  nombreUsuario: string;
  apellidosUsuario: string;
  email:string;

  //Datos de la categoria
  nombreCategoria: string;
}
