import { IServicios, NewServicios } from './servicios.model';

export const sampleWithRequiredData: IServicios = {
  id: 22155,
};

export const sampleWithPartialData: IServicios = {
  id: 29965,
  valorServicio: 15962.14,
  tipoServicio: 'ceramics zany demonstrate',
  descripcion: 'heavy cutover mozzarella',
};

export const sampleWithFullData: IServicios = {
  id: 4968,
  valorServicio: 6537.02,
  tipoServicio: 'inasmuch',
  descripcion: 'outlandish penalise chromakey',
  establecimientoId: 26568,
};

export const sampleWithNewData: NewServicios = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
