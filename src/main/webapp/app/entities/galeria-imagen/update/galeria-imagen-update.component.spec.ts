import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { GaleriaImagenService } from '../service/galeria-imagen.service';
import { IGaleriaImagen } from '../galeria-imagen.model';
import { GaleriaImagenFormService } from './galeria-imagen-form.service';

import { GaleriaImagenUpdateComponent } from './galeria-imagen-update.component';

describe('GaleriaImagen Management Update Component', () => {
  let comp: GaleriaImagenUpdateComponent;
  let fixture: ComponentFixture<GaleriaImagenUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let galeriaImagenFormService: GaleriaImagenFormService;
  let galeriaImagenService: GaleriaImagenService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GaleriaImagenUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(GaleriaImagenUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GaleriaImagenUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    galeriaImagenFormService = TestBed.inject(GaleriaImagenFormService);
    galeriaImagenService = TestBed.inject(GaleriaImagenService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const galeriaImagen: IGaleriaImagen = { id: 456 };

      activatedRoute.data = of({ galeriaImagen });
      comp.ngOnInit();

      expect(comp.galeriaImagen).toEqual(galeriaImagen);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGaleriaImagen>>();
      const galeriaImagen = { id: 123 };
      jest.spyOn(galeriaImagenFormService, 'getGaleriaImagen').mockReturnValue(galeriaImagen);
      jest.spyOn(galeriaImagenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ galeriaImagen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: galeriaImagen }));
      saveSubject.complete();

      // THEN
      expect(galeriaImagenFormService.getGaleriaImagen).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(galeriaImagenService.update).toHaveBeenCalledWith(expect.objectContaining(galeriaImagen));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGaleriaImagen>>();
      const galeriaImagen = { id: 123 };
      jest.spyOn(galeriaImagenFormService, 'getGaleriaImagen').mockReturnValue({ id: null });
      jest.spyOn(galeriaImagenService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ galeriaImagen: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: galeriaImagen }));
      saveSubject.complete();

      // THEN
      expect(galeriaImagenFormService.getGaleriaImagen).toHaveBeenCalled();
      expect(galeriaImagenService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGaleriaImagen>>();
      const galeriaImagen = { id: 123 };
      jest.spyOn(galeriaImagenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ galeriaImagen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(galeriaImagenService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
