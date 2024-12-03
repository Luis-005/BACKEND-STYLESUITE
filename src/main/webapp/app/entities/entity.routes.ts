import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'backend2App.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'categoria-producto',
    data: { pageTitle: 'backend2App.categoriaProducto.home.title' },
    loadChildren: () => import('./categoria-producto/categoria-producto.routes'),
  },
  {
    path: 'cita',
    data: { pageTitle: 'backend2App.cita.home.title' },
    loadChildren: () => import('./cita/cita.routes'),
  },
  {
    path: 'disponibilidad-empleado',
    data: { pageTitle: 'backend2App.disponibilidadEmpleado.home.title' },
    loadChildren: () => import('./disponibilidad-empleado/disponibilidad-empleado.routes'),
  },
  {
    path: 'empleado',
    data: { pageTitle: 'backend2App.empleado.home.title' },
    loadChildren: () => import('./empleado/empleado.routes'),
  },
  {
    path: 'establecimiento',
    data: { pageTitle: 'backend2App.establecimiento.home.title' },
    loadChildren: () => import('./establecimiento/establecimiento.routes'),
  },
  {
    path: 'galeria-imagen',
    data: { pageTitle: 'backend2App.galeriaImagen.home.title' },
    loadChildren: () => import('./galeria-imagen/galeria-imagen.routes'),
  },
  {
    path: 'pago',
    data: { pageTitle: 'backend2App.pago.home.title' },
    loadChildren: () => import('./pago/pago.routes'),
  },
  {
    path: 'persona',
    data: { pageTitle: 'backend2App.persona.home.title' },
    loadChildren: () => import('./persona/persona.routes'),
  },
  {
    path: 'producto',
    data: { pageTitle: 'backend2App.producto.home.title' },
    loadChildren: () => import('./producto/producto.routes'),
  },
  {
    path: 'servicios',
    data: { pageTitle: 'backend2App.servicios.home.title' },
    loadChildren: () => import('./servicios/servicios.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
