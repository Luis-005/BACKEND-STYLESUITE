export interface IEstablecimiento {
  id: number;
  nombre?: string | null;
  direccion?: string | null;
  telefono?: string | null;
  correoElectronico?: string | null;
  urlImg?: string | null;
  horaApertura?: string | null;
  horaCierre?: string | null;
  userId?: number | null;
}

export type NewEstablecimiento = Omit<IEstablecimiento, 'id'> & { id: null };
