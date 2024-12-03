import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IServicios } from '../servicios.model';

@Component({
  standalone: true,
  selector: 'jhi-servicios-detail',
  templateUrl: './servicios-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ServiciosDetailComponent {
  servicios = input<IServicios | null>(null);

  previousState(): void {
    window.history.back();
  }
}
