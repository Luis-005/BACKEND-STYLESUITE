import { IProducto, NewProducto } from './producto.model';

export const sampleWithRequiredData: IProducto = {
  id: 6231,
};

export const sampleWithPartialData: IProducto = {
  id: 8285,
  nombre: 'er',
};

export const sampleWithFullData: IProducto = {
  id: 4907,
  nombre: 'ack whoever',
  descripcion: 'pilot scarcely',
  precio: 8289.39,
  cantidad: 3973,
  urlImg: 'crocodile fabricate stay',
  categoriaProductoId: 30283,
  establecimientoId: 25270,
};

export const sampleWithNewData: NewProducto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
