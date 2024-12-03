import dayjs from 'dayjs/esm';

export interface IDisponibilidadEmpleado {
  id: number;
  diaSemana?: string | null;
  fechaInicio?: dayjs.Dayjs | null;
  fechaFin?: dayjs.Dayjs | null;
  empleadoId?: number | null;
}

export type NewDisponibilidadEmpleado = Omit<IDisponibilidadEmpleado, 'id'> & { id: null };
