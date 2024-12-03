import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IServicios, NewServicios } from '../servicios.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServicios for edit and NewServiciosFormGroupInput for create.
 */
type ServiciosFormGroupInput = IServicios | PartialWithRequiredKeyOf<NewServicios>;

type ServiciosFormDefaults = Pick<NewServicios, 'id'>;

type ServiciosFormGroupContent = {
  id: FormControl<IServicios['id'] | NewServicios['id']>;
  valorServicio: FormControl<IServicios['valorServicio']>;
  tipoServicio: FormControl<IServicios['tipoServicio']>;
  descripcion: FormControl<IServicios['descripcion']>;
  establecimientoId: FormControl<IServicios['establecimientoId']>;
};

export type ServiciosFormGroup = FormGroup<ServiciosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServiciosFormService {
  createServiciosFormGroup(servicios: ServiciosFormGroupInput = { id: null }): ServiciosFormGroup {
    const serviciosRawValue = {
      ...this.getFormDefaults(),
      ...servicios,
    };
    return new FormGroup<ServiciosFormGroupContent>({
      id: new FormControl(
        { value: serviciosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      valorServicio: new FormControl(serviciosRawValue.valorServicio),
      tipoServicio: new FormControl(serviciosRawValue.tipoServicio),
      descripcion: new FormControl(serviciosRawValue.descripcion),
      establecimientoId: new FormControl(serviciosRawValue.establecimientoId),
    });
  }

  getServicios(form: ServiciosFormGroup): IServicios | NewServicios {
    return form.getRawValue() as IServicios | NewServicios;
  }

  resetForm(form: ServiciosFormGroup, servicios: ServiciosFormGroupInput): void {
    const serviciosRawValue = { ...this.getFormDefaults(), ...servicios };
    form.reset(
      {
        ...serviciosRawValue,
        id: { value: serviciosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ServiciosFormDefaults {
    return {
      id: null,
    };
  }
}
