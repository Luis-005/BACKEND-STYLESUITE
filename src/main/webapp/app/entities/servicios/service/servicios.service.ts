import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServicios, NewServicios } from '../servicios.model';

export type PartialUpdateServicios = Partial<IServicios> & Pick<IServicios, 'id'>;

export type EntityResponseType = HttpResponse<IServicios>;
export type EntityArrayResponseType = HttpResponse<IServicios[]>;

@Injectable({ providedIn: 'root' })
export class ServiciosService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/servicios');

  create(servicios: NewServicios): Observable<EntityResponseType> {
    return this.http.post<IServicios>(this.resourceUrl, servicios, { observe: 'response' });
  }

  update(servicios: IServicios): Observable<EntityResponseType> {
    return this.http.put<IServicios>(`${this.resourceUrl}/${this.getServiciosIdentifier(servicios)}`, servicios, { observe: 'response' });
  }

  partialUpdate(servicios: PartialUpdateServicios): Observable<EntityResponseType> {
    return this.http.patch<IServicios>(`${this.resourceUrl}/${this.getServiciosIdentifier(servicios)}`, servicios, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServicios>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServicios[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServiciosIdentifier(servicios: Pick<IServicios, 'id'>): number {
    return servicios.id;
  }

  compareServicios(o1: Pick<IServicios, 'id'> | null, o2: Pick<IServicios, 'id'> | null): boolean {
    return o1 && o2 ? this.getServiciosIdentifier(o1) === this.getServiciosIdentifier(o2) : o1 === o2;
  }

  addServiciosToCollectionIfMissing<Type extends Pick<IServicios, 'id'>>(
    serviciosCollection: Type[],
    ...serviciosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const servicios: Type[] = serviciosToCheck.filter(isPresent);
    if (servicios.length > 0) {
      const serviciosCollectionIdentifiers = serviciosCollection.map(serviciosItem => this.getServiciosIdentifier(serviciosItem));
      const serviciosToAdd = servicios.filter(serviciosItem => {
        const serviciosIdentifier = this.getServiciosIdentifier(serviciosItem);
        if (serviciosCollectionIdentifiers.includes(serviciosIdentifier)) {
          return false;
        }
        serviciosCollectionIdentifiers.push(serviciosIdentifier);
        return true;
      });
      return [...serviciosToAdd, ...serviciosCollection];
    }
    return serviciosCollection;
  }
}
