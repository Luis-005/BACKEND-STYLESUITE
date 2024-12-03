import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { GaleriaImagenDetailComponent } from './galeria-imagen-detail.component';

describe('GaleriaImagen Management Detail Component', () => {
  let comp: GaleriaImagenDetailComponent;
  let fixture: ComponentFixture<GaleriaImagenDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GaleriaImagenDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./galeria-imagen-detail.component').then(m => m.GaleriaImagenDetailComponent),
              resolve: { galeriaImagen: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GaleriaImagenDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GaleriaImagenDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load galeriaImagen on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GaleriaImagenDetailComponent);

      // THEN
      expect(instance.galeriaImagen()).toEqual(expect.objectContaining({ id: 123 }));
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
