import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DisponibilidadEmpleadoResolve from './route/disponibilidad-empleado-routing-resolve.service';

const disponibilidadEmpleadoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/disponibilidad-empleado.component').then(m => m.DisponibilidadEmpleadoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/disponibilidad-empleado-detail.component').then(m => m.DisponibilidadEmpleadoDetailComponent),
    resolve: {
      disponibilidadEmpleado: DisponibilidadEmpleadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/disponibilidad-empleado-update.component').then(m => m.DisponibilidadEmpleadoUpdateComponent),
    resolve: {
      disponibilidadEmpleado: DisponibilidadEmpleadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/disponibilidad-empleado-update.component').then(m => m.DisponibilidadEmpleadoUpdateComponent),
    resolve: {
      disponibilidadEmpleado: DisponibilidadEmpleadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default disponibilidadEmpleadoRoute;
