import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'b2d50111-c8a7-4ecf-b2c6-f0bd54a788b5',
};

export const sampleWithPartialData: IAuthority = {
  name: '62a8a381-7266-45fb-b592-7c45b7a86515',
};

export const sampleWithFullData: IAuthority = {
  name: 'd1068cb4-db6b-454a-832a-f7509a1ac170',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
