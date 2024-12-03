import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EstablecimientoDetailComponent } from './establecimiento-detail.component';

describe('Establecimiento Management Detail Component', () => {
  let comp: EstablecimientoDetailComponent;
  let fixture: ComponentFixture<EstablecimientoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstablecimientoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./establecimiento-detail.component').then(m => m.EstablecimientoDetailComponent),
              resolve: { establecimiento: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EstablecimientoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EstablecimientoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load establecimiento on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EstablecimientoDetailComponent);

      // THEN
      expect(instance.establecimiento()).toEqual(expect.objectContaining({ id: 123 }));
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
