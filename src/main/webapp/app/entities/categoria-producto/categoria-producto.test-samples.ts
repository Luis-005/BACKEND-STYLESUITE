import { ICategoriaProducto, NewCategoriaProducto } from './categoria-producto.model';

export const sampleWithRequiredData: ICategoriaProducto = {
  id: 1912,
};

export const sampleWithPartialData: ICategoriaProducto = {
  id: 23727,
};

export const sampleWithFullData: ICategoriaProducto = {
  id: 5314,
  nombre: 'partially',
  establecimientoId: 5480,
};

export const sampleWithNewData: NewCategoriaProducto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
