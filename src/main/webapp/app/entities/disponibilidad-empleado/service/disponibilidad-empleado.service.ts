import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDisponibilidadEmpleado, NewDisponibilidadEmpleado } from '../disponibilidad-empleado.model';

export type PartialUpdateDisponibilidadEmpleado = Partial<IDisponibilidadEmpleado> & Pick<IDisponibilidadEmpleado, 'id'>;

type RestOf<T extends IDisponibilidadEmpleado | NewDisponibilidadEmpleado> = Omit<T, 'fechaInicio' | 'fechaFin'> & {
  fechaInicio?: string | null;
  fechaFin?: string | null;
};

export type RestDisponibilidadEmpleado = RestOf<IDisponibilidadEmpleado>;

export type NewRestDisponibilidadEmpleado = RestOf<NewDisponibilidadEmpleado>;

export type PartialUpdateRestDisponibilidadEmpleado = RestOf<PartialUpdateDisponibilidadEmpleado>;

export type EntityResponseType = HttpResponse<IDisponibilidadEmpleado>;
export type EntityArrayResponseType = HttpResponse<IDisponibilidadEmpleado[]>;

@Injectable({ providedIn: 'root' })
export class DisponibilidadEmpleadoService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/disponibilidad-empleados');

  create(disponibilidadEmpleado: NewDisponibilidadEmpleado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(disponibilidadEmpleado);
    return this.http
      .post<RestDisponibilidadEmpleado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(disponibilidadEmpleado: IDisponibilidadEmpleado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(disponibilidadEmpleado);
    return this.http
      .put<RestDisponibilidadEmpleado>(`${this.resourceUrl}/${this.getDisponibilidadEmpleadoIdentifier(disponibilidadEmpleado)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(disponibilidadEmpleado: PartialUpdateDisponibilidadEmpleado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(disponibilidadEmpleado);
    return this.http
      .patch<RestDisponibilidadEmpleado>(`${this.resourceUrl}/${this.getDisponibilidadEmpleadoIdentifier(disponibilidadEmpleado)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDisponibilidadEmpleado>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDisponibilidadEmpleado[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDisponibilidadEmpleadoIdentifier(disponibilidadEmpleado: Pick<IDisponibilidadEmpleado, 'id'>): number {
    return disponibilidadEmpleado.id;
  }

  compareDisponibilidadEmpleado(o1: Pick<IDisponibilidadEmpleado, 'id'> | null, o2: Pick<IDisponibilidadEmpleado, 'id'> | null): boolean {
    return o1 && o2 ? this.getDisponibilidadEmpleadoIdentifier(o1) === this.getDisponibilidadEmpleadoIdentifier(o2) : o1 === o2;
  }

  addDisponibilidadEmpleadoToCollectionIfMissing<Type extends Pick<IDisponibilidadEmpleado, 'id'>>(
    disponibilidadEmpleadoCollection: Type[],
    ...disponibilidadEmpleadosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const disponibilidadEmpleados: Type[] = disponibilidadEmpleadosToCheck.filter(isPresent);
    if (disponibilidadEmpleados.length > 0) {
      const disponibilidadEmpleadoCollectionIdentifiers = disponibilidadEmpleadoCollection.map(disponibilidadEmpleadoItem =>
        this.getDisponibilidadEmpleadoIdentifier(disponibilidadEmpleadoItem),
      );
      const disponibilidadEmpleadosToAdd = disponibilidadEmpleados.filter(disponibilidadEmpleadoItem => {
        const disponibilidadEmpleadoIdentifier = this.getDisponibilidadEmpleadoIdentifier(disponibilidadEmpleadoItem);
        if (disponibilidadEmpleadoCollectionIdentifiers.includes(disponibilidadEmpleadoIdentifier)) {
          return false;
        }
        disponibilidadEmpleadoCollectionIdentifiers.push(disponibilidadEmpleadoIdentifier);
        return true;
      });
      return [...disponibilidadEmpleadosToAdd, ...disponibilidadEmpleadoCollection];
    }
    return disponibilidadEmpleadoCollection;
  }

  protected convertDateFromClient<T extends IDisponibilidadEmpleado | NewDisponibilidadEmpleado | PartialUpdateDisponibilidadEmpleado>(
    disponibilidadEmpleado: T,
  ): RestOf<T> {
    return {
      ...disponibilidadEmpleado,
      fechaInicio: disponibilidadEmpleado.fechaInicio?.toJSON() ?? null,
      fechaFin: disponibilidadEmpleado.fechaFin?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDisponibilidadEmpleado: RestDisponibilidadEmpleado): IDisponibilidadEmpleado {
    return {
      ...restDisponibilidadEmpleado,
      fechaInicio: restDisponibilidadEmpleado.fechaInicio ? dayjs(restDisponibilidadEmpleado.fechaInicio) : undefined,
      fechaFin: restDisponibilidadEmpleado.fechaFin ? dayjs(restDisponibilidadEmpleado.fechaFin) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDisponibilidadEmpleado>): HttpResponse<IDisponibilidadEmpleado> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDisponibilidadEmpleado[]>): HttpResponse<IDisponibilidadEmpleado[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
