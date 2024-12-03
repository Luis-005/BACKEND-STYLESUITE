export interface ICategoriaProducto {
  id: number;
  nombre?: string | null;
  establecimientoId?: number | null;
}

export type NewCategoriaProducto = Omit<ICategoriaProducto, 'id'> & { id: null };
