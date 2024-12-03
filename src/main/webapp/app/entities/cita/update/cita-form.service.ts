import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICita, NewCita } from '../cita.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICita for edit and NewCitaFormGroupInput for create.
 */
type CitaFormGroupInput = ICita | PartialWithRequiredKeyOf<NewCita>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICita | NewCita> = Omit<T, 'fechaCita'> & {
  fechaCita?: string | null;
};

type CitaFormRawValue = FormValueOf<ICita>;

type NewCitaFormRawValue = FormValueOf<NewCita>;

type CitaFormDefaults = Pick<NewCita, 'id' | 'fechaCita'>;

type CitaFormGroupContent = {
  id: FormControl<CitaFormRawValue['id'] | NewCita['id']>;
  fechaCita: FormControl<CitaFormRawValue['fechaCita']>;
  duracion: FormControl<CitaFormRawValue['duracion']>;
  estado: FormControl<CitaFormRawValue['estado']>;
  personaId: FormControl<CitaFormRawValue['personaId']>;
  nombrePersona: FormControl<CitaFormRawValue['nombrePersona']>;
  establecimientoId: FormControl<CitaFormRawValue['establecimientoId']>;
  nombreEstablecimiento: FormControl<CitaFormRawValue['nombreEstablecimiento']>;
  servicioId: FormControl<CitaFormRawValue['servicioId']>;
  empleadoId: FormControl<CitaFormRawValue['empleadoId']>;
  nombreEmpleado: FormControl<CitaFormRawValue['nombreEmpleado']>;
  valorServicio: FormControl<CitaFormRawValue['valorServicio']>;
};

export type CitaFormGroup = FormGroup<CitaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CitaFormService {
  createCitaFormGroup(cita: CitaFormGroupInput = { id: null }): CitaFormGroup {
    const citaRawValue = this.convertCitaToCitaRawValue({
      ...this.getFormDefaults(),
      ...cita,
    });
    return new FormGroup<CitaFormGroupContent>({
      id: new FormControl(
        { value: citaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fechaCita: new FormControl(citaRawValue.fechaCita),
      duracion: new FormControl(citaRawValue.duracion),
      estado: new FormControl(citaRawValue.estado),
      personaId: new FormControl(citaRawValue.personaId),
      nombrePersona: new FormControl(citaRawValue.nombrePersona),
      establecimientoId: new FormControl(citaRawValue.establecimientoId),
      nombreEstablecimiento: new FormControl(citaRawValue.nombreEstablecimiento),
      servicioId: new FormControl(citaRawValue.servicioId),
      empleadoId: new FormControl(citaRawValue.empleadoId),
      nombreEmpleado: new FormControl(citaRawValue.nombreEmpleado),
      valorServicio: new FormControl(citaRawValue.valorServicio),
    });
  }

  getCita(form: CitaFormGroup): ICita | NewCita {
    return this.convertCitaRawValueToCita(form.getRawValue() as CitaFormRawValue | NewCitaFormRawValue);
  }

  resetForm(form: CitaFormGroup, cita: CitaFormGroupInput): void {
    const citaRawValue = this.convertCitaToCitaRawValue({ ...this.getFormDefaults(), ...cita });
    form.reset(
      {
        ...citaRawValue,
        id: { value: citaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CitaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fechaCita: currentTime,
    };
  }

  private convertCitaRawValueToCita(rawCita: CitaFormRawValue | NewCitaFormRawValue): ICita | NewCita {
    return {
      ...rawCita,
      fechaCita: dayjs(rawCita.fechaCita, DATE_TIME_FORMAT),
    };
  }

  private convertCitaToCitaRawValue(
    cita: ICita | (Partial<NewCita> & CitaFormDefaults),
  ): CitaFormRawValue | PartialWithRequiredKeyOf<NewCitaFormRawValue> {
    return {
      ...cita,
      fechaCita: cita.fechaCita ? cita.fechaCita.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
