import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGaleriaImagen } from '../galeria-imagen.model';
import { GaleriaImagenService } from '../service/galeria-imagen.service';

@Component({
  standalone: true,
  templateUrl: './galeria-imagen-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GaleriaImagenDeleteDialogComponent {
  galeriaImagen?: IGaleriaImagen;

  protected galeriaImagenService = inject(GaleriaImagenService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.galeriaImagenService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
