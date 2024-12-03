import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IServicios } from '../servicios.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../servicios.test-samples';

import { ServiciosService } from './servicios.service';

const requireRestSample: IServicios = {
  ...sampleWithRequiredData,
};

describe('Servicios Service', () => {
  let service: ServiciosService;
  let httpMock: HttpTestingController;
  let expectedResult: IServicios | IServicios[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ServiciosService);
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

    it('should create a Servicios', () => {
      const servicios = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(servicios).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Servicios', () => {
      const servicios = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(servicios).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Servicios', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Servicios', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Servicios', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServiciosToCollectionIfMissing', () => {
      it('should add a Servicios to an empty array', () => {
        const servicios: IServicios = sampleWithRequiredData;
        expectedResult = service.addServiciosToCollectionIfMissing([], servicios);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicios);
      });

      it('should not add a Servicios to an array that contains it', () => {
        const servicios: IServicios = sampleWithRequiredData;
        const serviciosCollection: IServicios[] = [
          {
            ...servicios,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServiciosToCollectionIfMissing(serviciosCollection, servicios);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Servicios to an array that doesn't contain it", () => {
        const servicios: IServicios = sampleWithRequiredData;
        const serviciosCollection: IServicios[] = [sampleWithPartialData];
        expectedResult = service.addServiciosToCollectionIfMissing(serviciosCollection, servicios);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicios);
      });

      it('should add only unique Servicios to an array', () => {
        const serviciosArray: IServicios[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const serviciosCollection: IServicios[] = [sampleWithRequiredData];
        expectedResult = service.addServiciosToCollectionIfMissing(serviciosCollection, ...serviciosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const servicios: IServicios = sampleWithRequiredData;
        const servicios2: IServicios = sampleWithPartialData;
        expectedResult = service.addServiciosToCollectionIfMissing([], servicios, servicios2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicios);
        expect(expectedResult).toContain(servicios2);
      });

      it('should accept null and undefined values', () => {
        const servicios: IServicios = sampleWithRequiredData;
        expectedResult = service.addServiciosToCollectionIfMissing([], null, servicios, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicios);
      });

      it('should return initial array if no Servicios is added', () => {
        const serviciosCollection: IServicios[] = [sampleWithRequiredData];
        expectedResult = service.addServiciosToCollectionIfMissing(serviciosCollection, undefined, null);
        expect(expectedResult).toEqual(serviciosCollection);
      });
    });

    describe('compareServicios', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareServicios(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareServicios(entity1, entity2);
        const compareResult2 = service.compareServicios(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareServicios(entity1, entity2);
        const compareResult2 = service.compareServicios(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareServicios(entity1, entity2);
        const compareResult2 = service.compareServicios(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
