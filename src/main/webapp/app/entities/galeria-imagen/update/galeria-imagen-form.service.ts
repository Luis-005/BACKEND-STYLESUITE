import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IGaleriaImagen, NewGaleriaImagen } from '../galeria-imagen.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGaleriaImagen for edit and NewGaleriaImagenFormGroupInput for create.
 */
type GaleriaImagenFormGroupInput = IGaleriaImagen | PartialWithRequiredKeyOf<NewGaleriaImagen>;

type GaleriaImagenFormDefaults = Pick<NewGaleriaImagen, 'id'>;

type GaleriaImagenFormGroupContent = {
  id: FormControl<IGaleriaImagen['id'] | NewGaleriaImagen['id']>;
  nombre: FormControl<IGaleriaImagen['nombre']>;
  descripcion: FormControl<IGaleriaImagen['descripcion']>;
  urlImagen: FormControl<IGaleriaImagen['urlImagen']>;
  establecimientoId: FormControl<IGaleriaImagen['establecimientoId']>;
};

export type GaleriaImagenFormGroup = FormGroup<GaleriaImagenFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GaleriaImagenFormService {
  createGaleriaImagenFormGroup(galeriaImagen: GaleriaImagenFormGroupInput = { id: null }): GaleriaImagenFormGroup {
    const galeriaImagenRawValue = {
      ...this.getFormDefaults(),
      ...galeriaImagen,
    };
    return new FormGroup<GaleriaImagenFormGroupContent>({
      id: new FormControl(
        { value: galeriaImagenRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(galeriaImagenRawValue.nombre),
      descripcion: new FormControl(galeriaImagenRawValue.descripcion),
      urlImagen: new FormControl(galeriaImagenRawValue.urlImagen),
      establecimientoId: new FormControl(galeriaImagenRawValue.establecimientoId),
    });
  }

  getGaleriaImagen(form: GaleriaImagenFormGroup): IGaleriaImagen | NewGaleriaImagen {
    return form.getRawValue() as IGaleriaImagen | NewGaleriaImagen;
  }

  resetForm(form: GaleriaImagenFormGroup, galeriaImagen: GaleriaImagenFormGroupInput): void {
    const galeriaImagenRawValue = { ...this.getFormDefaults(), ...galeriaImagen };
    form.reset(
      {
        ...galeriaImagenRawValue,
        id: { value: galeriaImagenRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GaleriaImagenFormDefaults {
    return {
      id: null,
    };
  }
}
