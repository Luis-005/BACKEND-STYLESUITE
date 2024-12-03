import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICategoriaProducto } from '../categoria-producto.model';
import { CategoriaProductoService } from '../service/categoria-producto.service';
import { CategoriaProductoFormGroup, CategoriaProductoFormService } from './categoria-producto-form.service';

@Component({
  standalone: true,
  selector: 'jhi-categoria-producto-update',
  templateUrl: './categoria-producto-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CategoriaProductoUpdateComponent implements OnInit {
  isSaving = false;
  categoriaProducto: ICategoriaProducto | null = null;

  protected categoriaProductoService = inject(CategoriaProductoService);
  protected categoriaProductoFormService = inject(CategoriaProductoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CategoriaProductoFormGroup = this.categoriaProductoFormService.createCategoriaProductoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaProducto }) => {
      this.categoriaProducto = categoriaProducto;
      if (categoriaProducto) {
        this.updateForm(categoriaProducto);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoriaProducto = this.categoriaProductoFormService.getCategoriaProducto(this.editForm);
    if (categoriaProducto.id !== null) {
      this.subscribeToSaveResponse(this.categoriaProductoService.update(categoriaProducto));
    } else {
      this.subscribeToSaveResponse(this.categoriaProductoService.create(categoriaProducto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaProducto>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(categoriaProducto: ICategoriaProducto): void {
    this.categoriaProducto = categoriaProducto;
    this.categoriaProductoFormService.resetForm(this.editForm, categoriaProducto);
  }
}
