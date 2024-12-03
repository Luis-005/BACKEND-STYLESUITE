import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDisponibilidadEmpleado } from '../disponibilidad-empleado.model';
import { DisponibilidadEmpleadoService } from '../service/disponibilidad-empleado.service';

@Component({
  standalone: true,
  templateUrl: './disponibilidad-empleado-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DisponibilidadEmpleadoDeleteDialogComponent {
  disponibilidadEmpleado?: IDisponibilidadEmpleado;

  protected disponibilidadEmpleadoService = inject(DisponibilidadEmpleadoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.disponibilidadEmpleadoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
