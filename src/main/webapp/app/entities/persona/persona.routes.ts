import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PersonaResolve from './route/persona-routing-resolve.service';

const personaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/persona.component').then(m => m.PersonaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/persona-detail.component').then(m => m.PersonaDetailComponent),
    resolve: {
      persona: PersonaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/persona-update.component').then(m => m.PersonaUpdateComponent),
    resolve: {
      persona: PersonaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/persona-update.component').then(m => m.PersonaUpdateComponent),
    resolve: {
      persona: PersonaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default personaRoute;
