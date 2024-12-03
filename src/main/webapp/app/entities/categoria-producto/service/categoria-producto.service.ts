import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategoriaProducto, NewCategoriaProducto } from '../categoria-producto.model';

export type PartialUpdateCategoriaProducto = Partial<ICategoriaProducto> & Pick<ICategoriaProducto, 'id'>;

export type EntityResponseType = HttpResponse<ICategoriaProducto>;
export type EntityArrayResponseType = HttpResponse<ICategoriaProducto[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaProductoService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categoria-productos');

  create(categoriaProducto: NewCategoriaProducto): Observable<EntityResponseType> {
    return this.http.post<ICategoriaProducto>(this.resourceUrl, categoriaProducto, { observe: 'response' });
  }

  update(categoriaProducto: ICategoriaProducto): Observable<EntityResponseType> {
    return this.http.put<ICategoriaProducto>(
      `${this.resourceUrl}/${this.getCategoriaProductoIdentifier(categoriaProducto)}`,
      categoriaProducto,
      { observe: 'response' },
    );
  }

  partialUpdate(categoriaProducto: PartialUpdateCategoriaProducto): Observable<EntityResponseType> {
    return this.http.patch<ICategoriaProducto>(
      `${this.resourceUrl}/${this.getCategoriaProductoIdentifier(categoriaProducto)}`,
      categoriaProducto,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoriaProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaProducto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategoriaProductoIdentifier(categoriaProducto: Pick<ICategoriaProducto, 'id'>): number {
    return categoriaProducto.id;
  }

  compareCategoriaProducto(o1: Pick<ICategoriaProducto, 'id'> | null, o2: Pick<ICategoriaProducto, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategoriaProductoIdentifier(o1) === this.getCategoriaProductoIdentifier(o2) : o1 === o2;
  }

  addCategoriaProductoToCollectionIfMissing<Type extends Pick<ICategoriaProducto, 'id'>>(
    categoriaProductoCollection: Type[],
    ...categoriaProductosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categoriaProductos: Type[] = categoriaProductosToCheck.filter(isPresent);
    if (categoriaProductos.length > 0) {
      const categoriaProductoCollectionIdentifiers = categoriaProductoCollection.map(categoriaProductoItem =>
        this.getCategoriaProductoIdentifier(categoriaProductoItem),
      );
      const categoriaProductosToAdd = categoriaProductos.filter(categoriaProductoItem => {
        const categoriaProductoIdentifier = this.getCategoriaProductoIdentifier(categoriaProductoItem);
        if (categoriaProductoCollectionIdentifiers.includes(categoriaProductoIdentifier)) {
          return false;
        }
        categoriaProductoCollectionIdentifiers.push(categoriaProductoIdentifier);
        return true;
      });
      return [...categoriaProductosToAdd, ...categoriaProductoCollection];
    }
    return categoriaProductoCollection;
  }
}
