import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { EstadoCitaEnum } from 'app/entities/enumerations/estado-cita-enum.model';
import { ICita } from '../cita.model';
import { CitaService } from '../service/cita.service';
import { CitaFormGroup, CitaFormService } from './cita-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cita-update',
  templateUrl: './cita-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CitaUpdateComponent implements OnInit {
  isSaving = false;
  cita: ICita | null = null;
  estadoCitaEnumValues = Object.keys(EstadoCitaEnum);

  protected citaService = inject(CitaService);
  protected citaFormService = inject(CitaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CitaFormGroup = this.citaFormService.createCitaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cita }) => {
      this.cita = cita;
      if (cita) {
        this.updateForm(cita);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cita = this.citaFormService.getCita(this.editForm);
    if (cita.id !== null) {
      this.subscribeToSaveResponse(this.citaService.update(cita));
    } else {
      this.subscribeToSaveResponse(this.citaService.create(cita));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICita>>): void {
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

  protected updateForm(cita: ICita): void {
    this.cita = cita;
    this.citaFormService.resetForm(this.editForm, cita);
  }
}
