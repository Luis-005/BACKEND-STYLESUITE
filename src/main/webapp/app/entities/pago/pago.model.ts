import dayjs from 'dayjs/esm';
import { MetodoPagoEnum } from 'app/entities/enumerations/metodo-pago-enum.model';

export interface IPago {
  id: number;
  monto?: number | null;
  fechaPago?: dayjs.Dayjs | null;
  metodoPago?: keyof typeof MetodoPagoEnum | null;
  estado?: string | null;
  citaId?: number | null;
  carritoId?: number | null;
  userId?: number | null;
}

export type NewPago = Omit<IPago, 'id'> & { id: null };
