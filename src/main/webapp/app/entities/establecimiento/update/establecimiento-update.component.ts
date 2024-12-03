import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEstablecimiento } from '../establecimiento.model';
import { EstablecimientoService } from '../service/establecimiento.service';
import { EstablecimientoFormGroup, EstablecimientoFormService } from './establecimiento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-establecimiento-update',
  templateUrl: './establecimiento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EstablecimientoUpdateComponent implements OnInit {
  isSaving = false;
  establecimiento: IEstablecimiento | null = null;

  protected establecimientoService = inject(EstablecimientoService);
  protected establecimientoFormService = inject(EstablecimientoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EstablecimientoFormGroup = this.establecimientoFormService.createEstablecimientoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ establecimiento }) => {
      this.establecimiento = establecimiento;
      if (establecimiento) {
        this.updateForm(establecimiento);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const establecimiento = this.establecimientoFormService.getEstablecimiento(this.editForm);
    if (establecimiento.id !== null) {
      this.subscribeToSaveResponse(this.establecimientoService.update(establecimiento));
    } else {
      this.subscribeToSaveResponse(this.establecimientoService.create(establecimiento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstablecimiento>>): void {
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

  protected updateForm(establecimiento: IEstablecimiento): void {
    this.establecimiento = establecimiento;
    this.establecimientoFormService.resetForm(this.editForm, establecimiento);
  }
}
