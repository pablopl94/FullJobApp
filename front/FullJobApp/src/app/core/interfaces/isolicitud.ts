export interface ISolicitud {
    idSolicitud: number;
    fecha: string;
    archivo: string;
    curriculum: string;
    comentarios: string;
    salario: number;
    estado: 'CREADA' | 'ACEPTADA' | 'RECHAZADA' | string;
    nombreEmpresa: string;
    nombreVacante: string;
    nombreUsuario: string;
    apellidosUsuario: string;
    nombreCategoria: string;
  }
  