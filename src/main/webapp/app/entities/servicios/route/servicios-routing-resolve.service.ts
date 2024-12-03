import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServicios } from '../servicios.model';
import { ServiciosService } from '../service/servicios.service';

const serviciosResolve = (route: ActivatedRouteSnapshot): Observable<null | IServicios> => {
  const id = route.params.id;
  if (id) {
    return inject(ServiciosService)
      .find(id)
      .pipe(
        mergeMap((servicios: HttpResponse<IServicios>) => {
          if (servicios.body) {
            return of(servicios.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default serviciosResolve;
