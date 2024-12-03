import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICategoriaProducto } from '../categoria-producto.model';
import { CategoriaProductoService } from '../service/categoria-producto.service';

@Component({
  standalone: true,
  templateUrl: './categoria-producto-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CategoriaProductoDeleteDialogComponent {
  categoriaProducto?: ICategoriaProducto;

  protected categoriaProductoService = inject(CategoriaProductoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriaProductoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
