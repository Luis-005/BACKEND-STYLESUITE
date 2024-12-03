import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../categoria-producto.test-samples';

import { CategoriaProductoFormService } from './categoria-producto-form.service';

describe('CategoriaProducto Form Service', () => {
  let service: CategoriaProductoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriaProductoFormService);
  });

  describe('Service methods', () => {
    describe('createCategoriaProductoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategoriaProductoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            establecimientoId: expect.any(Object),
          }),
        );
      });

      it('passing ICategoriaProducto should create a new form with FormGroup', () => {
        const formGroup = service.createCategoriaProductoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            establecimientoId: expect.any(Object),
          }),
        );
      });
    });

    describe('getCategoriaProducto', () => {
      it('should return NewCategoriaProducto for default CategoriaProducto initial value', () => {
        const formGroup = service.createCategoriaProductoFormGroup(sampleWithNewData);

        const categoriaProducto = service.getCategoriaProducto(formGroup) as any;

        expect(categoriaProducto).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategoriaProducto for empty CategoriaProducto initial value', () => {
        const formGroup = service.createCategoriaProductoFormGroup();

        const categoriaProducto = service.getCategoriaProducto(formGroup) as any;

        expect(categoriaProducto).toMatchObject({});
      });

      it('should return ICategoriaProducto', () => {
        const formGroup = service.createCategoriaProductoFormGroup(sampleWithRequiredData);

        const categoriaProducto = service.getCategoriaProducto(formGroup) as any;

        expect(categoriaProducto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategoriaProducto should not enable id FormControl', () => {
        const formGroup = service.createCategoriaProductoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategoriaProducto should disable id FormControl', () => {
        const formGroup = service.createCategoriaProductoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
