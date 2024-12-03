import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import GaleriaImagenResolve from './route/galeria-imagen-routing-resolve.service';

const galeriaImagenRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/galeria-imagen.component').then(m => m.GaleriaImagenComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/galeria-imagen-detail.component').then(m => m.GaleriaImagenDetailComponent),
    resolve: {
      galeriaImagen: GaleriaImagenResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/galeria-imagen-update.component').then(m => m.GaleriaImagenUpdateComponent),
    resolve: {
      galeriaImagen: GaleriaImagenResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/galeria-imagen-update.component').then(m => m.GaleriaImagenUpdateComponent),
    resolve: {
      galeriaImagen: GaleriaImagenResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default galeriaImagenRoute;
