export interface IGaleriaImagen {
  id: number;
  nombre?: string | null;
  descripcion?: string | null;
  urlImagen?: string | null;
  establecimientoId?: number | null;
}

export type NewGaleriaImagen = Omit<IGaleriaImagen, 'id'> & { id: null };
