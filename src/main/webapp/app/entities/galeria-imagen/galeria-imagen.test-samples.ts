import { IGaleriaImagen, NewGaleriaImagen } from './galeria-imagen.model';

export const sampleWithRequiredData: IGaleriaImagen = {
  id: 17365,
};

export const sampleWithPartialData: IGaleriaImagen = {
  id: 9349,
  nombre: 'wherever than',
  establecimientoId: 1304,
};

export const sampleWithFullData: IGaleriaImagen = {
  id: 21761,
  nombre: 'wisely male lobster',
  descripcion: 'up',
  urlImagen: 'likewise against',
  establecimientoId: 21782,
};

export const sampleWithNewData: NewGaleriaImagen = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
