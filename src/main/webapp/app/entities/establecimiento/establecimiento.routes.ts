import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import EstablecimientoResolve from './route/establecimiento-routing-resolve.service';

const establecimientoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/establecimiento.component').then(m => m.EstablecimientoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/establecimiento-detail.component').then(m => m.EstablecimientoDetailComponent),
    resolve: {
      establecimiento: EstablecimientoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/establecimiento-update.component').then(m => m.EstablecimientoUpdateComponent),
    resolve: {
      establecimiento: EstablecimientoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/establecimiento-update.component').then(m => m.EstablecimientoUpdateComponent),
    resolve: {
      establecimiento: EstablecimientoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default establecimientoRoute;
