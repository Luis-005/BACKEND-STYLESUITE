import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IDisponibilidadEmpleado } from '../disponibilidad-empleado.model';
import { DisponibilidadEmpleadoService } from '../service/disponibilidad-empleado.service';

import disponibilidadEmpleadoResolve from './disponibilidad-empleado-routing-resolve.service';

describe('DisponibilidadEmpleado routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: DisponibilidadEmpleadoService;
  let resultDisponibilidadEmpleado: IDisponibilidadEmpleado | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(DisponibilidadEmpleadoService);
    resultDisponibilidadEmpleado = undefined;
  });

  describe('resolve', () => {
    it('should return IDisponibilidadEmpleado returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        disponibilidadEmpleadoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDisponibilidadEmpleado = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDisponibilidadEmpleado).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        disponibilidadEmpleadoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDisponibilidadEmpleado = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultDisponibilidadEmpleado).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDisponibilidadEmpleado>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        disponibilidadEmpleadoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDisponibilidadEmpleado = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDisponibilidadEmpleado).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
