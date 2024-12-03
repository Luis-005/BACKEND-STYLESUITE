import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ServiciosService } from '../service/servicios.service';
import { IServicios } from '../servicios.model';
import { ServiciosFormService } from './servicios-form.service';

import { ServiciosUpdateComponent } from './servicios-update.component';

describe('Servicios Management Update Component', () => {
  let comp: ServiciosUpdateComponent;
  let fixture: ComponentFixture<ServiciosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let serviciosFormService: ServiciosFormService;
  let serviciosService: ServiciosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ServiciosUpdateComponent],
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
      .overrideTemplate(ServiciosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServiciosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    serviciosFormService = TestBed.inject(ServiciosFormService);
    serviciosService = TestBed.inject(ServiciosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const servicios: IServicios = { id: 456 };

      activatedRoute.data = of({ servicios });
      comp.ngOnInit();

      expect(comp.servicios).toEqual(servicios);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicios>>();
      const servicios = { id: 123 };
      jest.spyOn(serviciosFormService, 'getServicios').mockReturnValue(servicios);
      jest.spyOn(serviciosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicios });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicios }));
      saveSubject.complete();

      // THEN
      expect(serviciosFormService.getServicios).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(serviciosService.update).toHaveBeenCalledWith(expect.objectContaining(servicios));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicios>>();
      const servicios = { id: 123 };
      jest.spyOn(serviciosFormService, 'getServicios').mockReturnValue({ id: null });
      jest.spyOn(serviciosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicios: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicios }));
      saveSubject.complete();

      // THEN
      expect(serviciosFormService.getServicios).toHaveBeenCalled();
      expect(serviciosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicios>>();
      const servicios = { id: 123 };
      jest.spyOn(serviciosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicios });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(serviciosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
