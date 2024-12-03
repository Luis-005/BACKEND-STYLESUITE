import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IServicios } from '../servicios.model';
import { ServiciosService } from '../service/servicios.service';
import { ServiciosFormGroup, ServiciosFormService } from './servicios-form.service';

@Component({
  standalone: true,
  selector: 'jhi-servicios-update',
  templateUrl: './servicios-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ServiciosUpdateComponent implements OnInit {
  isSaving = false;
  servicios: IServicios | null = null;

  protected serviciosService = inject(ServiciosService);
  protected serviciosFormService = inject(ServiciosFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ServiciosFormGroup = this.serviciosFormService.createServiciosFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ servicios }) => {
      this.servicios = servicios;
      if (servicios) {
        this.updateForm(servicios);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const servicios = this.serviciosFormService.getServicios(this.editForm);
    if (servicios.id !== null) {
      this.subscribeToSaveResponse(this.serviciosService.update(servicios));
    } else {
      this.subscribeToSaveResponse(this.serviciosService.create(servicios));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServicios>>): void {
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

  protected updateForm(servicios: IServicios): void {
    this.servicios = servicios;
    this.serviciosFormService.resetForm(this.editForm, servicios);
  }
}
