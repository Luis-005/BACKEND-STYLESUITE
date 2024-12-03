import dayjs from 'dayjs/esm';

import { ICita, NewCita } from './cita.model';

export const sampleWithRequiredData: ICita = {
  id: 31739,
};

export const sampleWithPartialData: ICita = {
  id: 32578,
  fechaCita: dayjs('2024-12-02T16:28'),
  estado: 'CANCELADA',
  personaId: 13417,
  nombreEstablecimiento: 'valiantly jot',
  servicioId: 25401,
  empleadoId: 6574,
};

export const sampleWithFullData: ICita = {
  id: 11337,
  fechaCita: dayjs('2024-12-01T22:09'),
  duracion: 18002,
  estado: 'OTRO',
  personaId: 20661,
  nombrePersona: 'loaf',
  establecimientoId: 4064,
  nombreEstablecimiento: 'what develop',
  servicioId: 14318,
  empleadoId: 26445,
  nombreEmpleado: 'intently',
  valorServicio: 1391.32,
};

export const sampleWithNewData: NewCita = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
