import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IGaleriaImagen } from '../galeria-imagen.model';
import { GaleriaImagenService } from '../service/galeria-imagen.service';
import { GaleriaImagenFormGroup, GaleriaImagenFormService } from './galeria-imagen-form.service';

@Component({
  standalone: true,
  selector: 'jhi-galeria-imagen-update',
  templateUrl: './galeria-imagen-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GaleriaImagenUpdateComponent implements OnInit {
  isSaving = false;
  galeriaImagen: IGaleriaImagen | null = null;

  protected galeriaImagenService = inject(GaleriaImagenService);
  protected galeriaImagenFormService = inject(GaleriaImagenFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GaleriaImagenFormGroup = this.galeriaImagenFormService.createGaleriaImagenFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ galeriaImagen }) => {
      this.galeriaImagen = galeriaImagen;
      if (galeriaImagen) {
        this.updateForm(galeriaImagen);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const galeriaImagen = this.galeriaImagenFormService.getGaleriaImagen(this.editForm);
    if (galeriaImagen.id !== null) {
      this.subscribeToSaveResponse(this.galeriaImagenService.update(galeriaImagen));
    } else {
      this.subscribeToSaveResponse(this.galeriaImagenService.create(galeriaImagen));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGaleriaImagen>>): void {
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

  protected updateForm(galeriaImagen: IGaleriaImagen): void {
    this.galeriaImagen = galeriaImagen;
    this.galeriaImagenFormService.resetForm(this.editForm, galeriaImagen);
  }
}
