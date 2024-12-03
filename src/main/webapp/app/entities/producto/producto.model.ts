export interface IProducto {
  id: number;
  nombre?: string | null;
  descripcion?: string | null;
  precio?: number | null;
  cantidad?: number | null;
  urlImg?: string | null;
  categoriaProductoId?: number | null;
  establecimientoId?: number | null;
}

export type NewProducto = Omit<IProducto, 'id'> & { id: null };
