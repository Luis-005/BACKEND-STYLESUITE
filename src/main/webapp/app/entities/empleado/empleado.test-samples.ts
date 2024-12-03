import { IEmpleado, NewEmpleado } from './empleado.model';

export const sampleWithRequiredData: IEmpleado = {
  id: 15988,
};

export const sampleWithPartialData: IEmpleado = {
  id: 16374,
  nombre: 'for',
  apellido: 'tightly',
  urlmg: 'along',
  estado: 'OCUPADO',
  establecimientoId: 16411,
};

export const sampleWithFullData: IEmpleado = {
  id: 27567,
  nombre: 'dual',
  apellido: 'barring yuck',
  cargo: 'though',
  salario: 20175.39,
  urlmg: 'hence',
  estado: 'OCUPADO',
  establecimientoId: 26392,
};

export const sampleWithNewData: NewEmpleado = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
