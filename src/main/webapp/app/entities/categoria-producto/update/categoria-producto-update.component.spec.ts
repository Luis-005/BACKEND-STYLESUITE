import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CategoriaProductoService } from '../service/categoria-producto.service';
import { ICategoriaProducto } from '../categoria-producto.model';
import { CategoriaProductoFormService } from './categoria-producto-form.service';

import { CategoriaProductoUpdateComponent } from './categoria-producto-update.component';

describe('CategoriaProducto Management Update Component', () => {
  let comp: CategoriaProductoUpdateComponent;
  let fixture: ComponentFixture<CategoriaProductoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categoriaProductoFormService: CategoriaProductoFormService;
  let categoriaProductoService: CategoriaProductoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CategoriaProductoUpdateComponent],
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
      .overrideTemplate(CategoriaProductoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategoriaProductoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categoriaProductoFormService = TestBed.inject(CategoriaProductoFormService);
    categoriaProductoService = TestBed.inject(CategoriaProductoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const categoriaProducto: ICategoriaProducto = { id: 456 };

      activatedRoute.data = of({ categoriaProducto });
      comp.ngOnInit();

      expect(comp.categoriaProducto).toEqual(categoriaProducto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaProducto>>();
      const categoriaProducto = { id: 123 };
      jest.spyOn(categoriaProductoFormService, 'getCategoriaProducto').mockReturnValue(categoriaProducto);
      jest.spyOn(categoriaProductoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaProducto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoriaProducto }));
      saveSubject.complete();

      // THEN
      expect(categoriaProductoFormService.getCategoriaProducto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categoriaProductoService.update).toHaveBeenCalledWith(expect.objectContaining(categoriaProducto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaProducto>>();
      const categoriaProducto = { id: 123 };
      jest.spyOn(categoriaProductoFormService, 'getCategoriaProducto').mockReturnValue({ id: null });
      jest.spyOn(categoriaProductoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaProducto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoriaProducto }));
      saveSubject.complete();

      // THEN
      expect(categoriaProductoFormService.getCategoriaProducto).toHaveBeenCalled();
      expect(categoriaProductoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaProducto>>();
      const categoriaProducto = { id: 123 };
      jest.spyOn(categoriaProductoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaProducto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categoriaProductoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
