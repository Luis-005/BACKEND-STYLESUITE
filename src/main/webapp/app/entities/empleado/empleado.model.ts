import { EstadoEmpleadoEnum } from 'app/entities/enumerations/estado-empleado-enum.model';

export interface IEmpleado {
  id: number;
  nombre?: string | null;
  apellido?: string | null;
  cargo?: string | null;
  salario?: number | null;
  urlmg?: string | null;
  estado?: keyof typeof EstadoEmpleadoEnum | null;
  establecimientoId?: number | null;
}

export type NewEmpleado = Omit<IEmpleado, 'id'> & { id: null };
