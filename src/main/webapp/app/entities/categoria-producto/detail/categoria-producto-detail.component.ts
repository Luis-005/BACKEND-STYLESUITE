import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ICategoriaProducto } from '../categoria-producto.model';

@Component({
  standalone: true,
  selector: 'jhi-categoria-producto-detail',
  templateUrl: './categoria-producto-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CategoriaProductoDetailComponent {
  categoriaProducto = input<ICategoriaProducto | null>(null);

  previousState(): void {
    window.history.back();
  }
}
