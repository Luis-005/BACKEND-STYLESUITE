import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../establecimiento.test-samples';

import { EstablecimientoFormService } from './establecimiento-form.service';

describe('Establecimiento Form Service', () => {
  let service: EstablecimientoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EstablecimientoFormService);
  });

  describe('Service methods', () => {
    describe('createEstablecimientoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEstablecimientoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            direccion: expect.any(Object),
            telefono: expect.any(Object),
            correoElectronico: expect.any(Object),
            urlImg: expect.any(Object),
            horaApertura: expect.any(Object),
            horaCierre: expect.any(Object),
            userId: expect.any(Object),
          }),
        );
      });

      it('passing IEstablecimiento should create a new form with FormGroup', () => {
        const formGroup = service.createEstablecimientoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            direccion: expect.any(Object),
            telefono: expect.any(Object),
            correoElectronico: expect.any(Object),
            urlImg: expect.any(Object),
            horaApertura: expect.any(Object),
            horaCierre: expect.any(Object),
            userId: expect.any(Object),
          }),
        );
      });
    });

    describe('getEstablecimiento', () => {
      it('should return NewEstablecimiento for default Establecimiento initial value', () => {
        const formGroup = service.createEstablecimientoFormGroup(sampleWithNewData);

        const establecimiento = service.getEstablecimiento(formGroup) as any;

        expect(establecimiento).toMatchObject(sampleWithNewData);
      });

      it('should return NewEstablecimiento for empty Establecimiento initial value', () => {
        const formGroup = service.createEstablecimientoFormGroup();

        const establecimiento = service.getEstablecimiento(formGroup) as any;

        expect(establecimiento).toMatchObject({});
      });

      it('should return IEstablecimiento', () => {
        const formGroup = service.createEstablecimientoFormGroup(sampleWithRequiredData);

        const establecimiento = service.getEstablecimiento(formGroup) as any;

        expect(establecimiento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEstablecimiento should not enable id FormControl', () => {
        const formGroup = service.createEstablecimientoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEstablecimiento should disable id FormControl', () => {
        const formGroup = service.createEstablecimientoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
