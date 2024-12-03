import { IPersona, NewPersona } from './persona.model';

export const sampleWithRequiredData: IPersona = {
  id: 28212,
};

export const sampleWithPartialData: IPersona = {
  id: 25720,
  nombre: 'baggy',
  urlImg: 'near gosh',
  telefono: 'secret gah as',
};

export const sampleWithFullData: IPersona = {
  id: 4900,
  nombre: 'microblog',
  apellido: 'questioningly jagged so',
  urlImg: 'on solidly gloomy',
  userId: 27655,
  telefono: 'brace saloon annually',
  urlmg: 'premeditation jive',
};

export const sampleWithNewData: NewPersona = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
