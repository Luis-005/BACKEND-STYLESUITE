import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DisponibilidadEmpleadoDetailComponent } from './disponibilidad-empleado-detail.component';

describe('DisponibilidadEmpleado Management Detail Component', () => {
  let comp: DisponibilidadEmpleadoDetailComponent;
  let fixture: ComponentFixture<DisponibilidadEmpleadoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DisponibilidadEmpleadoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./disponibilidad-empleado-detail.component').then(m => m.DisponibilidadEmpleadoDetailComponent),
              resolve: { disponibilidadEmpleado: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DisponibilidadEmpleadoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DisponibilidadEmpleadoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load disponibilidadEmpleado on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DisponibilidadEmpleadoDetailComponent);

      // THEN
      expect(instance.disponibilidadEmpleado()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
