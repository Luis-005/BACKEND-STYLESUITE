import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDisponibilidadEmpleado } from '../disponibilidad-empleado.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../disponibilidad-empleado.test-samples';

import { DisponibilidadEmpleadoService, RestDisponibilidadEmpleado } from './disponibilidad-empleado.service';

const requireRestSample: RestDisponibilidadEmpleado = {
  ...sampleWithRequiredData,
  fechaInicio: sampleWithRequiredData.fechaInicio?.toJSON(),
  fechaFin: sampleWithRequiredData.fechaFin?.toJSON(),
};

describe('DisponibilidadEmpleado Service', () => {
  let service: DisponibilidadEmpleadoService;
  let httpMock: HttpTestingController;
  let expectedResult: IDisponibilidadEmpleado | IDisponibilidadEmpleado[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DisponibilidadEmpleadoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a DisponibilidadEmpleado', () => {
      const disponibilidadEmpleado = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(disponibilidadEmpleado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DisponibilidadEmpleado', () => {
      const disponibilidadEmpleado = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(disponibilidadEmpleado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DisponibilidadEmpleado', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DisponibilidadEmpleado', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DisponibilidadEmpleado', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDisponibilidadEmpleadoToCollectionIfMissing', () => {
      it('should add a DisponibilidadEmpleado to an empty array', () => {
        const disponibilidadEmpleado: IDisponibilidadEmpleado = sampleWithRequiredData;
        expectedResult = service.addDisponibilidadEmpleadoToCollectionIfMissing([], disponibilidadEmpleado);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(disponibilidadEmpleado);
      });

      it('should not add a DisponibilidadEmpleado to an array that contains it', () => {
        const disponibilidadEmpleado: IDisponibilidadEmpleado = sampleWithRequiredData;
        const disponibilidadEmpleadoCollection: IDisponibilidadEmpleado[] = [
          {
            ...disponibilidadEmpleado,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDisponibilidadEmpleadoToCollectionIfMissing(disponibilidadEmpleadoCollection, disponibilidadEmpleado);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DisponibilidadEmpleado to an array that doesn't contain it", () => {
        const disponibilidadEmpleado: IDisponibilidadEmpleado = sampleWithRequiredData;
        const disponibilidadEmpleadoCollection: IDisponibilidadEmpleado[] = [sampleWithPartialData];
        expectedResult = service.addDisponibilidadEmpleadoToCollectionIfMissing(disponibilidadEmpleadoCollection, disponibilidadEmpleado);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(disponibilidadEmpleado);
      });

      it('should add only unique DisponibilidadEmpleado to an array', () => {
        const disponibilidadEmpleadoArray: IDisponibilidadEmpleado[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const disponibilidadEmpleadoCollection: IDisponibilidadEmpleado[] = [sampleWithRequiredData];
        expectedResult = service.addDisponibilidadEmpleadoToCollectionIfMissing(
          disponibilidadEmpleadoCollection,
          ...disponibilidadEmpleadoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const disponibilidadEmpleado: IDisponibilidadEmpleado = sampleWithRequiredData;
        const disponibilidadEmpleado2: IDisponibilidadEmpleado = sampleWithPartialData;
        expectedResult = service.addDisponibilidadEmpleadoToCollectionIfMissing([], disponibilidadEmpleado, disponibilidadEmpleado2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(disponibilidadEmpleado);
        expect(expectedResult).toContain(disponibilidadEmpleado2);
      });

      it('should accept null and undefined values', () => {
        const disponibilidadEmpleado: IDisponibilidadEmpleado = sampleWithRequiredData;
        expectedResult = service.addDisponibilidadEmpleadoToCollectionIfMissing([], null, disponibilidadEmpleado, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(disponibilidadEmpleado);
      });

      it('should return initial array if no DisponibilidadEmpleado is added', () => {
        const disponibilidadEmpleadoCollection: IDisponibilidadEmpleado[] = [sampleWithRequiredData];
        expectedResult = service.addDisponibilidadEmpleadoToCollectionIfMissing(disponibilidadEmpleadoCollection, undefined, null);
        expect(expectedResult).toEqual(disponibilidadEmpleadoCollection);
      });
    });

    describe('compareDisponibilidadEmpleado', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDisponibilidadEmpleado(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDisponibilidadEmpleado(entity1, entity2);
        const compareResult2 = service.compareDisponibilidadEmpleado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDisponibilidadEmpleado(entity1, entity2);
        const compareResult2 = service.compareDisponibilidadEmpleado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDisponibilidadEmpleado(entity1, entity2);
        const compareResult2 = service.compareDisponibilidadEmpleado(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
