import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDisponibilidadEmpleado } from '../disponibilidad-empleado.model';
import { DisponibilidadEmpleadoService } from '../service/disponibilidad-empleado.service';

const disponibilidadEmpleadoResolve = (route: ActivatedRouteSnapshot): Observable<null | IDisponibilidadEmpleado> => {
  const id = route.params.id;
  if (id) {
    return inject(DisponibilidadEmpleadoService)
      .find(id)
      .pipe(
        mergeMap((disponibilidadEmpleado: HttpResponse<IDisponibilidadEmpleado>) => {
          if (disponibilidadEmpleado.body) {
            return of(disponibilidadEmpleado.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default disponibilidadEmpleadoResolve;
