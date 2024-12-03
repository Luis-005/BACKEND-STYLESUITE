import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGaleriaImagen } from '../galeria-imagen.model';
import { GaleriaImagenService } from '../service/galeria-imagen.service';

const galeriaImagenResolve = (route: ActivatedRouteSnapshot): Observable<null | IGaleriaImagen> => {
  const id = route.params.id;
  if (id) {
    return inject(GaleriaImagenService)
      .find(id)
      .pipe(
        mergeMap((galeriaImagen: HttpResponse<IGaleriaImagen>) => {
          if (galeriaImagen.body) {
            return of(galeriaImagen.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default galeriaImagenResolve;
