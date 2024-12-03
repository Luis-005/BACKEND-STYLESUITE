import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IProducto } from '../producto.model';

@Component({
  standalone: true,
  selector: 'jhi-producto-detail',
  templateUrl: './producto-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProductoDetailComponent {
  producto = input<IProducto | null>(null);

  previousState(): void {
    window.history.back();
  }
}
