import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstablecimiento, NewEstablecimiento } from '../establecimiento.model';

export type PartialUpdateEstablecimiento = Partial<IEstablecimiento> & Pick<IEstablecimiento, 'id'>;

export type EntityResponseType = HttpResponse<IEstablecimiento>;
export type EntityArrayResponseType = HttpResponse<IEstablecimiento[]>;

@Injectable({ providedIn: 'root' })
export class EstablecimientoService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/establecimientos');

  create(establecimiento: NewEstablecimiento): Observable<EntityResponseType> {
    return this.http.post<IEstablecimiento>(this.resourceUrl, establecimiento, { observe: 'response' });
  }

  update(establecimiento: IEstablecimiento): Observable<EntityResponseType> {
    return this.http.put<IEstablecimiento>(`${this.resourceUrl}/${this.getEstablecimientoIdentifier(establecimiento)}`, establecimiento, {
      observe: 'response',
    });
  }

  partialUpdate(establecimiento: PartialUpdateEstablecimiento): Observable<EntityResponseType> {
    return this.http.patch<IEstablecimiento>(`${this.resourceUrl}/${this.getEstablecimientoIdentifier(establecimiento)}`, establecimiento, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstablecimiento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstablecimiento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEstablecimientoIdentifier(establecimiento: Pick<IEstablecimiento, 'id'>): number {
    return establecimiento.id;
  }

  compareEstablecimiento(o1: Pick<IEstablecimiento, 'id'> | null, o2: Pick<IEstablecimiento, 'id'> | null): boolean {
    return o1 && o2 ? this.getEstablecimientoIdentifier(o1) === this.getEstablecimientoIdentifier(o2) : o1 === o2;
  }

  addEstablecimientoToCollectionIfMissing<Type extends Pick<IEstablecimiento, 'id'>>(
    establecimientoCollection: Type[],
    ...establecimientosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const establecimientos: Type[] = establecimientosToCheck.filter(isPresent);
    if (establecimientos.length > 0) {
      const establecimientoCollectionIdentifiers = establecimientoCollection.map(establecimientoItem =>
        this.getEstablecimientoIdentifier(establecimientoItem),
      );
      const establecimientosToAdd = establecimientos.filter(establecimientoItem => {
        const establecimientoIdentifier = this.getEstablecimientoIdentifier(establecimientoItem);
        if (establecimientoCollectionIdentifiers.includes(establecimientoIdentifier)) {
          return false;
        }
        establecimientoCollectionIdentifiers.push(establecimientoIdentifier);
        return true;
      });
      return [...establecimientosToAdd, ...establecimientoCollection];
    }
    return establecimientoCollection;
  }
}
