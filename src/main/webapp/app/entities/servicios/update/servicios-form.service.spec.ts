import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../servicios.test-samples';

import { ServiciosFormService } from './servicios-form.service';

describe('Servicios Form Service', () => {
  let service: ServiciosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiciosFormService);
  });

  describe('Service methods', () => {
    describe('createServiciosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createServiciosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valorServicio: expect.any(Object),
            tipoServicio: expect.any(Object),
            descripcion: expect.any(Object),
            establecimientoId: expect.any(Object),
          }),
        );
      });

      it('passing IServicios should create a new form with FormGroup', () => {
        const formGroup = service.createServiciosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valorServicio: expect.any(Object),
            tipoServicio: expect.any(Object),
            descripcion: expect.any(Object),
            establecimientoId: expect.any(Object),
          }),
        );
      });
    });

    describe('getServicios', () => {
      it('should return NewServicios for default Servicios initial value', () => {
        const formGroup = service.createServiciosFormGroup(sampleWithNewData);

        const servicios = service.getServicios(formGroup) as any;

        expect(servicios).toMatchObject(sampleWithNewData);
      });

      it('should return NewServicios for empty Servicios initial value', () => {
        const formGroup = service.createServiciosFormGroup();

        const servicios = service.getServicios(formGroup) as any;

        expect(servicios).toMatchObject({});
      });

      it('should return IServicios', () => {
        const formGroup = service.createServiciosFormGroup(sampleWithRequiredData);

        const servicios = service.getServicios(formGroup) as any;

        expect(servicios).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IServicios should not enable id FormControl', () => {
        const formGroup = service.createServiciosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewServicios should disable id FormControl', () => {
        const formGroup = service.createServiciosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
