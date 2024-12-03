import dayjs from 'dayjs/esm';
import { EstadoCitaEnum } from 'app/entities/enumerations/estado-cita-enum.model';

export interface ICita {
  id: number;
  fechaCita?: dayjs.Dayjs | null;
  duracion?: number | null;
  estado?: keyof typeof EstadoCitaEnum | null;
  personaId?: number | null;
  nombrePersona?: string | null;
  establecimientoId?: number | null;
  nombreEstablecimiento?: string | null;
  servicioId?: number | null;
  empleadoId?: number | null;
  nombreEmpleado?: string | null;
  valorServicio?: number | null;
}

export type NewCita = Omit<ICita, 'id'> & { id: null };
