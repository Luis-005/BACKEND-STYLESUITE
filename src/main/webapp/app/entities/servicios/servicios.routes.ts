import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ServiciosResolve from './route/servicios-routing-resolve.service';

const serviciosRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/servicios.component').then(m => m.ServiciosComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/servicios-detail.component').then(m => m.ServiciosDetailComponent),
    resolve: {
      servicios: ServiciosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/servicios-update.component').then(m => m.ServiciosUpdateComponent),
    resolve: {
      servicios: ServiciosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/servicios-update.component').then(m => m.ServiciosUpdateComponent),
    resolve: {
      servicios: ServiciosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default serviciosRoute;
