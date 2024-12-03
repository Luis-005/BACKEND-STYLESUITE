import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IPersona } from '../persona.model';

@Component({
  standalone: true,
  selector: 'jhi-persona-detail',
  templateUrl: './persona-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PersonaDetailComponent {
  persona = input<IPersona | null>(null);

  previousState(): void {
    window.history.back();
  }
}
