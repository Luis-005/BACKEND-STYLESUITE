import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IServicios } from '../servicios.model';
import { ServiciosService } from '../service/servicios.service';

@Component({
  standalone: true,
  templateUrl: './servicios-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ServiciosDeleteDialogComponent {
  servicios?: IServicios;

  protected serviciosService = inject(ServiciosService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviciosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
