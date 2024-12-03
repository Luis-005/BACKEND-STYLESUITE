import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IEstablecimiento } from '../establecimiento.model';

@Component({
  standalone: true,
  selector: 'jhi-establecimiento-detail',
  templateUrl: './establecimiento-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EstablecimientoDetailComponent {
  establecimiento = input<IEstablecimiento | null>(null);

  previousState(): void {
    window.history.back();
  }
}
