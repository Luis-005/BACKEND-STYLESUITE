import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDisponibilidadEmpleado } from '../disponibilidad-empleado.model';
import { DisponibilidadEmpleadoService } from '../service/disponibilidad-empleado.service';
import { DisponibilidadEmpleadoFormGroup, DisponibilidadEmpleadoFormService } from './disponibilidad-empleado-form.service';

@Component({
  standalone: true,
  selector: 'jhi-disponibilidad-empleado-update',
  templateUrl: './disponibilidad-empleado-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DisponibilidadEmpleadoUpdateComponent implements OnInit {
  isSaving = false;
  disponibilidadEmpleado: IDisponibilidadEmpleado | null = null;

  protected disponibilidadEmpleadoService = inject(DisponibilidadEmpleadoService);
  protected disponibilidadEmpleadoFormService = inject(DisponibilidadEmpleadoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DisponibilidadEmpleadoFormGroup = this.disponibilidadEmpleadoFormService.createDisponibilidadEmpleadoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disponibilidadEmpleado }) => {
      this.disponibilidadEmpleado = disponibilidadEmpleado;
      if (disponibilidadEmpleado) {
        this.updateForm(disponibilidadEmpleado);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const disponibilidadEmpleado = this.disponibilidadEmpleadoFormService.getDisponibilidadEmpleado(this.editForm);
    if (disponibilidadEmpleado.id !== null) {
      this.subscribeToSaveResponse(this.disponibilidadEmpleadoService.update(disponibilidadEmpleado));
    } else {
      this.subscribeToSaveResponse(this.disponibilidadEmpleadoService.create(disponibilidadEmpleado));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDisponibilidadEmpleado>>): void {
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

  protected updateForm(disponibilidadEmpleado: IDisponibilidadEmpleado): void {
    this.disponibilidadEmpleado = disponibilidadEmpleado;
    this.disponibilidadEmpleadoFormService.resetForm(this.editForm, disponibilidadEmpleado);
  }
}
