import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CategoriaProductoResolve from './route/categoria-producto-routing-resolve.service';

const categoriaProductoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/categoria-producto.component').then(m => m.CategoriaProductoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/categoria-producto-detail.component').then(m => m.CategoriaProductoDetailComponent),
    resolve: {
      categoriaProducto: CategoriaProductoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/categoria-producto-update.component').then(m => m.CategoriaProductoUpdateComponent),
    resolve: {
      categoriaProducto: CategoriaProductoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/categoria-producto-update.component').then(m => m.CategoriaProductoUpdateComponent),
    resolve: {
      categoriaProducto: CategoriaProductoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default categoriaProductoRoute;
