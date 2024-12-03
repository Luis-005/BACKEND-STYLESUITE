import { IEstablecimiento, NewEstablecimiento } from './establecimiento.model';

export const sampleWithRequiredData: IEstablecimiento = {
  id: 31967,
};

export const sampleWithPartialData: IEstablecimiento = {
  id: 29926,
  nombre: 'hastily whereas giant',
  telefono: 'hypothesize for',
  correoElectronico: 'violently ack including',
  urlImg: 'break',
  horaCierre: 'frill',
};

export const sampleWithFullData: IEstablecimiento = {
  id: 5773,
  nombre: 'gah',
  direccion: 'shrilly',
  telefono: 'phew nice nippy',
  correoElectronico: 'joyous upset',
  urlImg: 'limply',
  horaApertura: 'aha',
  horaCierre: 'micro',
  userId: 21183,
};

export const sampleWithNewData: NewEstablecimiento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
