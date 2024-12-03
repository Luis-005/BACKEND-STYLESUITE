import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IDisponibilidadEmpleado } from '../disponibilidad-empleado.model';

@Component({
  standalone: true,
  selector: 'jhi-disponibilidad-empleado-detail',
  templateUrl: './disponibilidad-empleado-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DisponibilidadEmpleadoDetailComponent {
  disponibilidadEmpleado = input<IDisponibilidadEmpleado | null>(null);

  previousState(): void {
    window.history.back();
  }
}
