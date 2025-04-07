export interface Vacante {
  id_vacante: number;
  nombre: string;
  descripcion: string;
  detalles: string;
  fecha: string;
  salario: number;
  estatus: 'CREADA' | 'CUBIERTA' | 'CANCELADA';
  destacado: boolean;
  imagen: string;
  id_Categoria: number;
  id_empresa: number;
}
