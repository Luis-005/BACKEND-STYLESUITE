import dayjs from 'dayjs/esm';

import { IPago, NewPago } from './pago.model';

export const sampleWithRequiredData: IPago = {
  id: 28800,
};

export const sampleWithPartialData: IPago = {
  id: 13380,
  monto: 29778.5,
  fechaPago: dayjs('2024-12-02T01:19'),
  metodoPago: 'TRANSFERENCIA',
  estado: 'impact',
  userId: 22916,
};

export const sampleWithFullData: IPago = {
  id: 6948,
  monto: 250.91,
  fechaPago: dayjs('2024-12-02T11:22'),
  metodoPago: 'EFECTIVO',
  estado: 'ouch',
  citaId: 12001,
  carritoId: 1384,
  userId: 18815,
};

export const sampleWithNewData: NewPago = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
