import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICategoriaProducto, NewCategoriaProducto } from '../categoria-producto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategoriaProducto for edit and NewCategoriaProductoFormGroupInput for create.
 */
type CategoriaProductoFormGroupInput = ICategoriaProducto | PartialWithRequiredKeyOf<NewCategoriaProducto>;

type CategoriaProductoFormDefaults = Pick<NewCategoriaProducto, 'id'>;

type CategoriaProductoFormGroupContent = {
  id: FormControl<ICategoriaProducto['id'] | NewCategoriaProducto['id']>;
  nombre: FormControl<ICategoriaProducto['nombre']>;
  establecimientoId: FormControl<ICategoriaProducto['establecimientoId']>;
};

export type CategoriaProductoFormGroup = FormGroup<CategoriaProductoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategoriaProductoFormService {
  createCategoriaProductoFormGroup(categoriaProducto: CategoriaProductoFormGroupInput = { id: null }): CategoriaProductoFormGroup {
    const categoriaProductoRawValue = {
      ...this.getFormDefaults(),
      ...categoriaProducto,
    };
    return new FormGroup<CategoriaProductoFormGroupContent>({
      id: new FormControl(
        { value: categoriaProductoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(categoriaProductoRawValue.nombre),
      establecimientoId: new FormControl(categoriaProductoRawValue.establecimientoId),
    });
  }

  getCategoriaProducto(form: CategoriaProductoFormGroup): ICategoriaProducto | NewCategoriaProducto {
    return form.getRawValue() as ICategoriaProducto | NewCategoriaProducto;
  }

  resetForm(form: CategoriaProductoFormGroup, categoriaProducto: CategoriaProductoFormGroupInput): void {
    const categoriaProductoRawValue = { ...this.getFormDefaults(), ...categoriaProducto };
    form.reset(
      {
        ...categoriaProductoRawValue,
        id: { value: categoriaProductoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CategoriaProductoFormDefaults {
    return {
      id: null,
    };
  }
}
