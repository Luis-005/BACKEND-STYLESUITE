import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEstablecimiento, NewEstablecimiento } from '../establecimiento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEstablecimiento for edit and NewEstablecimientoFormGroupInput for create.
 */
type EstablecimientoFormGroupInput = IEstablecimiento | PartialWithRequiredKeyOf<NewEstablecimiento>;

type EstablecimientoFormDefaults = Pick<NewEstablecimiento, 'id'>;

type EstablecimientoFormGroupContent = {
  id: FormControl<IEstablecimiento['id'] | NewEstablecimiento['id']>;
  nombre: FormControl<IEstablecimiento['nombre']>;
  direccion: FormControl<IEstablecimiento['direccion']>;
  telefono: FormControl<IEstablecimiento['telefono']>;
  correoElectronico: FormControl<IEstablecimiento['correoElectronico']>;
  urlImg: FormControl<IEstablecimiento['urlImg']>;
  horaApertura: FormControl<IEstablecimiento['horaApertura']>;
  horaCierre: FormControl<IEstablecimiento['horaCierre']>;
  userId: FormControl<IEstablecimiento['userId']>;
};

export type EstablecimientoFormGroup = FormGroup<EstablecimientoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EstablecimientoFormService {
  createEstablecimientoFormGroup(establecimiento: EstablecimientoFormGroupInput = { id: null }): EstablecimientoFormGroup {
    const establecimientoRawValue = {
      ...this.getFormDefaults(),
      ...establecimiento,
    };
    return new FormGroup<EstablecimientoFormGroupContent>({
      id: new FormControl(
        { value: establecimientoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(establecimientoRawValue.nombre),
      direccion: new FormControl(establecimientoRawValue.direccion),
      telefono: new FormControl(establecimientoRawValue.telefono),
      correoElectronico: new FormControl(establecimientoRawValue.correoElectronico),
      urlImg: new FormControl(establecimientoRawValue.urlImg),
      horaApertura: new FormControl(establecimientoRawValue.horaApertura, {
        validators: [Validators.maxLength(5)],
      }),
      horaCierre: new FormControl(establecimientoRawValue.horaCierre, {
        validators: [Validators.maxLength(5)],
      }),
      userId: new FormControl(establecimientoRawValue.userId),
    });
  }

  getEstablecimiento(form: EstablecimientoFormGroup): IEstablecimiento | NewEstablecimiento {
    return form.getRawValue() as IEstablecimiento | NewEstablecimiento;
  }

  resetForm(form: EstablecimientoFormGroup, establecimiento: EstablecimientoFormGroupInput): void {
    const establecimientoRawValue = { ...this.getFormDefaults(), ...establecimiento };
    form.reset(
      {
        ...establecimientoRawValue,
        id: { value: establecimientoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EstablecimientoFormDefaults {
    return {
      id: null,
    };
  }
}
