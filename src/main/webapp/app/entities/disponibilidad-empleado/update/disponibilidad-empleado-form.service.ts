import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDisponibilidadEmpleado, NewDisponibilidadEmpleado } from '../disponibilidad-empleado.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDisponibilidadEmpleado for edit and NewDisponibilidadEmpleadoFormGroupInput for create.
 */
type DisponibilidadEmpleadoFormGroupInput = IDisponibilidadEmpleado | PartialWithRequiredKeyOf<NewDisponibilidadEmpleado>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDisponibilidadEmpleado | NewDisponibilidadEmpleado> = Omit<T, 'fechaInicio' | 'fechaFin'> & {
  fechaInicio?: string | null;
  fechaFin?: string | null;
};

type DisponibilidadEmpleadoFormRawValue = FormValueOf<IDisponibilidadEmpleado>;

type NewDisponibilidadEmpleadoFormRawValue = FormValueOf<NewDisponibilidadEmpleado>;

type DisponibilidadEmpleadoFormDefaults = Pick<NewDisponibilidadEmpleado, 'id' | 'fechaInicio' | 'fechaFin'>;

type DisponibilidadEmpleadoFormGroupContent = {
  id: FormControl<DisponibilidadEmpleadoFormRawValue['id'] | NewDisponibilidadEmpleado['id']>;
  diaSemana: FormControl<DisponibilidadEmpleadoFormRawValue['diaSemana']>;
  fechaInicio: FormControl<DisponibilidadEmpleadoFormRawValue['fechaInicio']>;
  fechaFin: FormControl<DisponibilidadEmpleadoFormRawValue['fechaFin']>;
  empleadoId: FormControl<DisponibilidadEmpleadoFormRawValue['empleadoId']>;
};

export type DisponibilidadEmpleadoFormGroup = FormGroup<DisponibilidadEmpleadoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DisponibilidadEmpleadoFormService {
  createDisponibilidadEmpleadoFormGroup(
    disponibilidadEmpleado: DisponibilidadEmpleadoFormGroupInput = { id: null },
  ): DisponibilidadEmpleadoFormGroup {
    const disponibilidadEmpleadoRawValue = this.convertDisponibilidadEmpleadoToDisponibilidadEmpleadoRawValue({
      ...this.getFormDefaults(),
      ...disponibilidadEmpleado,
    });
    return new FormGroup<DisponibilidadEmpleadoFormGroupContent>({
      id: new FormControl(
        { value: disponibilidadEmpleadoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      diaSemana: new FormControl(disponibilidadEmpleadoRawValue.diaSemana),
      fechaInicio: new FormControl(disponibilidadEmpleadoRawValue.fechaInicio),
      fechaFin: new FormControl(disponibilidadEmpleadoRawValue.fechaFin),
      empleadoId: new FormControl(disponibilidadEmpleadoRawValue.empleadoId),
    });
  }

  getDisponibilidadEmpleado(form: DisponibilidadEmpleadoFormGroup): IDisponibilidadEmpleado | NewDisponibilidadEmpleado {
    return this.convertDisponibilidadEmpleadoRawValueToDisponibilidadEmpleado(
      form.getRawValue() as DisponibilidadEmpleadoFormRawValue | NewDisponibilidadEmpleadoFormRawValue,
    );
  }

  resetForm(form: DisponibilidadEmpleadoFormGroup, disponibilidadEmpleado: DisponibilidadEmpleadoFormGroupInput): void {
    const disponibilidadEmpleadoRawValue = this.convertDisponibilidadEmpleadoToDisponibilidadEmpleadoRawValue({
      ...this.getFormDefaults(),
      ...disponibilidadEmpleado,
    });
    form.reset(
      {
        ...disponibilidadEmpleadoRawValue,
        id: { value: disponibilidadEmpleadoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DisponibilidadEmpleadoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fechaInicio: currentTime,
      fechaFin: currentTime,
    };
  }

  private convertDisponibilidadEmpleadoRawValueToDisponibilidadEmpleado(
    rawDisponibilidadEmpleado: DisponibilidadEmpleadoFormRawValue | NewDisponibilidadEmpleadoFormRawValue,
  ): IDisponibilidadEmpleado | NewDisponibilidadEmpleado {
    return {
      ...rawDisponibilidadEmpleado,
      fechaInicio: dayjs(rawDisponibilidadEmpleado.fechaInicio, DATE_TIME_FORMAT),
      fechaFin: dayjs(rawDisponibilidadEmpleado.fechaFin, DATE_TIME_FORMAT),
    };
  }

  private convertDisponibilidadEmpleadoToDisponibilidadEmpleadoRawValue(
    disponibilidadEmpleado: IDisponibilidadEmpleado | (Partial<NewDisponibilidadEmpleado> & DisponibilidadEmpleadoFormDefaults),
  ): DisponibilidadEmpleadoFormRawValue | PartialWithRequiredKeyOf<NewDisponibilidadEmpleadoFormRawValue> {
    return {
      ...disponibilidadEmpleado,
      fechaInicio: disponibilidadEmpleado.fechaInicio ? disponibilidadEmpleado.fechaInicio.format(DATE_TIME_FORMAT) : undefined,
      fechaFin: disponibilidadEmpleado.fechaFin ? disponibilidadEmpleado.fechaFin.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
