import dayjs from 'dayjs/esm';

import { IDisponibilidadEmpleado, NewDisponibilidadEmpleado } from './disponibilidad-empleado.model';

export const sampleWithRequiredData: IDisponibilidadEmpleado = {
  id: 11465,
};

export const sampleWithPartialData: IDisponibilidadEmpleado = {
  id: 28402,
  diaSemana: 'spherical',
};

export const sampleWithFullData: IDisponibilidadEmpleado = {
  id: 18816,
  diaSemana: 'developing',
  fechaInicio: dayjs('2024-12-01T23:49'),
  fechaFin: dayjs('2024-12-02T06:10'),
  empleadoId: 32542,
};

export const sampleWithNewData: NewDisponibilidadEmpleado = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
