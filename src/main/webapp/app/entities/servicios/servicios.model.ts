export interface IServicios {
  id: number;
  valorServicio?: number | null;
  tipoServicio?: string | null;
  descripcion?: string | null;
  establecimientoId?: number | null;
}

export type NewServicios = Omit<IServicios, 'id'> & { id: null };
