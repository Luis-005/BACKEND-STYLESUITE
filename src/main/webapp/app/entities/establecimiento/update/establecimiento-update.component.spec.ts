import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { EstablecimientoService } from '../service/establecimiento.service';
import { IEstablecimiento } from '../establecimiento.model';
import { EstablecimientoFormService } from './establecimiento-form.service';

import { EstablecimientoUpdateComponent } from './establecimiento-update.component';

describe('Establecimiento Management Update Component', () => {
  let comp: EstablecimientoUpdateComponent;
  let fixture: ComponentFixture<EstablecimientoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let establecimientoFormService: EstablecimientoFormService;
  let establecimientoService: EstablecimientoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EstablecimientoUpdateComponent],
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
      .overrideTemplate(EstablecimientoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EstablecimientoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    establecimientoFormService = TestBed.inject(EstablecimientoFormService);
    establecimientoService = TestBed.inject(EstablecimientoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const establecimiento: IEstablecimiento = { id: 456 };

      activatedRoute.data = of({ establecimiento });
      comp.ngOnInit();

      expect(comp.establecimiento).toEqual(establecimiento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstablecimiento>>();
      const establecimiento = { id: 123 };
      jest.spyOn(establecimientoFormService, 'getEstablecimiento').mockReturnValue(establecimiento);
      jest.spyOn(establecimientoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ establecimiento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: establecimiento }));
      saveSubject.complete();

      // THEN
      expect(establecimientoFormService.getEstablecimiento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(establecimientoService.update).toHaveBeenCalledWith(expect.objectContaining(establecimiento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstablecimiento>>();
      const establecimiento = { id: 123 };
      jest.spyOn(establecimientoFormService, 'getEstablecimiento').mockReturnValue({ id: null });
      jest.spyOn(establecimientoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ establecimiento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: establecimiento }));
      saveSubject.complete();

      // THEN
      expect(establecimientoFormService.getEstablecimiento).toHaveBeenCalled();
      expect(establecimientoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstablecimiento>>();
      const establecimiento = { id: 123 };
      jest.spyOn(establecimientoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ establecimiento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(establecimientoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
