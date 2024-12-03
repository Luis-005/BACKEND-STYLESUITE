import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGaleriaImagen, NewGaleriaImagen } from '../galeria-imagen.model';

export type PartialUpdateGaleriaImagen = Partial<IGaleriaImagen> & Pick<IGaleriaImagen, 'id'>;

export type EntityResponseType = HttpResponse<IGaleriaImagen>;
export type EntityArrayResponseType = HttpResponse<IGaleriaImagen[]>;

@Injectable({ providedIn: 'root' })
export class GaleriaImagenService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/galeria-imagens');

  create(galeriaImagen: NewGaleriaImagen): Observable<EntityResponseType> {
    return this.http.post<IGaleriaImagen>(this.resourceUrl, galeriaImagen, { observe: 'response' });
  }

  update(galeriaImagen: IGaleriaImagen): Observable<EntityResponseType> {
    return this.http.put<IGaleriaImagen>(`${this.resourceUrl}/${this.getGaleriaImagenIdentifier(galeriaImagen)}`, galeriaImagen, {
      observe: 'response',
    });
  }

  partialUpdate(galeriaImagen: PartialUpdateGaleriaImagen): Observable<EntityResponseType> {
    return this.http.patch<IGaleriaImagen>(`${this.resourceUrl}/${this.getGaleriaImagenIdentifier(galeriaImagen)}`, galeriaImagen, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGaleriaImagen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGaleriaImagen[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGaleriaImagenIdentifier(galeriaImagen: Pick<IGaleriaImagen, 'id'>): number {
    return galeriaImagen.id;
  }

  compareGaleriaImagen(o1: Pick<IGaleriaImagen, 'id'> | null, o2: Pick<IGaleriaImagen, 'id'> | null): boolean {
    return o1 && o2 ? this.getGaleriaImagenIdentifier(o1) === this.getGaleriaImagenIdentifier(o2) : o1 === o2;
  }

  addGaleriaImagenToCollectionIfMissing<Type extends Pick<IGaleriaImagen, 'id'>>(
    galeriaImagenCollection: Type[],
    ...galeriaImagensToCheck: (Type | null | undefined)[]
  ): Type[] {
    const galeriaImagens: Type[] = galeriaImagensToCheck.filter(isPresent);
    if (galeriaImagens.length > 0) {
      const galeriaImagenCollectionIdentifiers = galeriaImagenCollection.map(galeriaImagenItem =>
        this.getGaleriaImagenIdentifier(galeriaImagenItem),
      );
      const galeriaImagensToAdd = galeriaImagens.filter(galeriaImagenItem => {
        const galeriaImagenIdentifier = this.getGaleriaImagenIdentifier(galeriaImagenItem);
        if (galeriaImagenCollectionIdentifiers.includes(galeriaImagenIdentifier)) {
          return false;
        }
        galeriaImagenCollectionIdentifiers.push(galeriaImagenIdentifier);
        return true;
      });
      return [...galeriaImagensToAdd, ...galeriaImagenCollection];
    }
    return galeriaImagenCollection;
  }
}
