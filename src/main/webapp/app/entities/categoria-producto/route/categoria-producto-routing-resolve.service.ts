import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategoriaProducto } from '../categoria-producto.model';
import { CategoriaProductoService } from '../service/categoria-producto.service';

const categoriaProductoResolve = (route: ActivatedRouteSnapshot): Observable<null | ICategoriaProducto> => {
  const id = route.params.id;
  if (id) {
    return inject(CategoriaProductoService)
      .find(id)
      .pipe(
        mergeMap((categoriaProducto: HttpResponse<ICategoriaProducto>) => {
          if (categoriaProducto.body) {
            return of(categoriaProducto.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default categoriaProductoResolve;
