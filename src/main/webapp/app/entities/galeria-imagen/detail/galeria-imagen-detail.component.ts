import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IGaleriaImagen } from '../galeria-imagen.model';

@Component({
  standalone: true,
  selector: 'jhi-galeria-imagen-detail',
  templateUrl: './galeria-imagen-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class GaleriaImagenDetailComponent {
  galeriaImagen = input<IGaleriaImagen | null>(null);

  previousState(): void {
    window.history.back();
  }
}
