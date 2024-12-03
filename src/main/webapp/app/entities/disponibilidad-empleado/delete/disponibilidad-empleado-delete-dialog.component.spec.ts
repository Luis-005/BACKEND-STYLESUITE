jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { DisponibilidadEmpleadoService } from '../service/disponibilidad-empleado.service';

import { DisponibilidadEmpleadoDeleteDialogComponent } from './disponibilidad-empleado-delete-dialog.component';

describe('DisponibilidadEmpleado Management Delete Component', () => {
  let comp: DisponibilidadEmpleadoDeleteDialogComponent;
  let fixture: ComponentFixture<DisponibilidadEmpleadoDeleteDialogComponent>;
  let service: DisponibilidadEmpleadoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DisponibilidadEmpleadoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(DisponibilidadEmpleadoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DisponibilidadEmpleadoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DisponibilidadEmpleadoService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
