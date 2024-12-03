import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../disponibilidad-empleado.test-samples';

import { DisponibilidadEmpleadoFormService } from './disponibilidad-empleado-form.service';

describe('DisponibilidadEmpleado Form Service', () => {
  let service: DisponibilidadEmpleadoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DisponibilidadEmpleadoFormService);
  });

  describe('Service methods', () => {
    describe('createDisponibilidadEmpleadoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDisponibilidadEmpleadoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diaSemana: expect.any(Object),
            fechaInicio: expect.any(Object),
            fechaFin: expect.any(Object),
            empleadoId: expect.any(Object),
          }),
        );
      });

      it('passing IDisponibilidadEmpleado should create a new form with FormGroup', () => {
        const formGroup = service.createDisponibilidadEmpleadoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diaSemana: expect.any(Object),
            fechaInicio: expect.any(Object),
            fechaFin: expect.any(Object),
            empleadoId: expect.any(Object),
          }),
        );
      });
    });

    describe('getDisponibilidadEmpleado', () => {
      it('should return NewDisponibilidadEmpleado for default DisponibilidadEmpleado initial value', () => {
        const formGroup = service.createDisponibilidadEmpleadoFormGroup(sampleWithNewData);

        const disponibilidadEmpleado = service.getDisponibilidadEmpleado(formGroup) as any;

        expect(disponibilidadEmpleado).toMatchObject(sampleWithNewData);
      });

      it('should return NewDisponibilidadEmpleado for empty DisponibilidadEmpleado initial value', () => {
        const formGroup = service.createDisponibilidadEmpleadoFormGroup();

        const disponibilidadEmpleado = service.getDisponibilidadEmpleado(formGroup) as any;

        expect(disponibilidadEmpleado).toMatchObject({});
      });

      it('should return IDisponibilidadEmpleado', () => {
        const formGroup = service.createDisponibilidadEmpleadoFormGroup(sampleWithRequiredData);

        const disponibilidadEmpleado = service.getDisponibilidadEmpleado(formGroup) as any;

        expect(disponibilidadEmpleado).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDisponibilidadEmpleado should not enable id FormControl', () => {
        const formGroup = service.createDisponibilidadEmpleadoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDisponibilidadEmpleado should disable id FormControl', () => {
        const formGroup = service.createDisponibilidadEmpleadoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
