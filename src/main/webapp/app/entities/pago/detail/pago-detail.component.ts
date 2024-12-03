import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IPago } from '../pago.model';

@Component({
  standalone: true,
  selector: 'jhi-pago-detail',
  templateUrl: './pago-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PagoDetailComponent {
  pago = input<IPago | null>(null);

  previousState(): void {
    window.history.back();
  }
}
