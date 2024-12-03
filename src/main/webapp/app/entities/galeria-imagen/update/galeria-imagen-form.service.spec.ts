import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../galeria-imagen.test-samples';

import { GaleriaImagenFormService } from './galeria-imagen-form.service';

describe('GaleriaImagen Form Service', () => {
  let service: GaleriaImagenFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GaleriaImagenFormService);
  });

  describe('Service methods', () => {
    describe('createGaleriaImagenFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGaleriaImagenFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            descripcion: expect.any(Object),
            urlImagen: expect.any(Object),
            establecimientoId: expect.any(Object),
          }),
        );
      });

      it('passing IGaleriaImagen should create a new form with FormGroup', () => {
        const formGroup = service.createGaleriaImagenFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            descripcion: expect.any(Object),
            urlImagen: expect.any(Object),
            establecimientoId: expect.any(Object),
          }),
        );
      });
    });

    describe('getGaleriaImagen', () => {
      it('should return NewGaleriaImagen for default GaleriaImagen initial value', () => {
        const formGroup = service.createGaleriaImagenFormGroup(sampleWithNewData);

        const galeriaImagen = service.getGaleriaImagen(formGroup) as any;

        expect(galeriaImagen).toMatchObject(sampleWithNewData);
      });

      it('should return NewGaleriaImagen for empty GaleriaImagen initial value', () => {
        const formGroup = service.createGaleriaImagenFormGroup();

        const galeriaImagen = service.getGaleriaImagen(formGroup) as any;

        expect(galeriaImagen).toMatchObject({});
      });

      it('should return IGaleriaImagen', () => {
        const formGroup = service.createGaleriaImagenFormGroup(sampleWithRequiredData);

        const galeriaImagen = service.getGaleriaImagen(formGroup) as any;

        expect(galeriaImagen).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGaleriaImagen should not enable id FormControl', () => {
        const formGroup = service.createGaleriaImagenFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGaleriaImagen should disable id FormControl', () => {
        const formGroup = service.createGaleriaImagenFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
