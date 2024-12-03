import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEstablecimiento } from '../establecimiento.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../establecimiento.test-samples';

import { EstablecimientoService } from './establecimiento.service';

const requireRestSample: IEstablecimiento = {
  ...sampleWithRequiredData,
};

describe('Establecimiento Service', () => {
  let service: EstablecimientoService;
  let httpMock: HttpTestingController;
  let expectedResult: IEstablecimiento | IEstablecimiento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EstablecimientoService);
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

    it('should create a Establecimiento', () => {
      const establecimiento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(establecimiento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Establecimiento', () => {
      const establecimiento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(establecimiento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Establecimiento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Establecimiento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Establecimiento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEstablecimientoToCollectionIfMissing', () => {
      it('should add a Establecimiento to an empty array', () => {
        const establecimiento: IEstablecimiento = sampleWithRequiredData;
        expectedResult = service.addEstablecimientoToCollectionIfMissing([], establecimiento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(establecimiento);
      });

      it('should not add a Establecimiento to an array that contains it', () => {
        const establecimiento: IEstablecimiento = sampleWithRequiredData;
        const establecimientoCollection: IEstablecimiento[] = [
          {
            ...establecimiento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEstablecimientoToCollectionIfMissing(establecimientoCollection, establecimiento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Establecimiento to an array that doesn't contain it", () => {
        const establecimiento: IEstablecimiento = sampleWithRequiredData;
        const establecimientoCollection: IEstablecimiento[] = [sampleWithPartialData];
        expectedResult = service.addEstablecimientoToCollectionIfMissing(establecimientoCollection, establecimiento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(establecimiento);
      });

      it('should add only unique Establecimiento to an array', () => {
        const establecimientoArray: IEstablecimiento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const establecimientoCollection: IEstablecimiento[] = [sampleWithRequiredData];
        expectedResult = service.addEstablecimientoToCollectionIfMissing(establecimientoCollection, ...establecimientoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const establecimiento: IEstablecimiento = sampleWithRequiredData;
        const establecimiento2: IEstablecimiento = sampleWithPartialData;
        expectedResult = service.addEstablecimientoToCollectionIfMissing([], establecimiento, establecimiento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(establecimiento);
        expect(expectedResult).toContain(establecimiento2);
      });

      it('should accept null and undefined values', () => {
        const establecimiento: IEstablecimiento = sampleWithRequiredData;
        expectedResult = service.addEstablecimientoToCollectionIfMissing([], null, establecimiento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(establecimiento);
      });

      it('should return initial array if no Establecimiento is added', () => {
        const establecimientoCollection: IEstablecimiento[] = [sampleWithRequiredData];
        expectedResult = service.addEstablecimientoToCollectionIfMissing(establecimientoCollection, undefined, null);
        expect(expectedResult).toEqual(establecimientoCollection);
      });
    });

    describe('compareEstablecimiento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEstablecimiento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEstablecimiento(entity1, entity2);
        const compareResult2 = service.compareEstablecimiento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEstablecimiento(entity1, entity2);
        const compareResult2 = service.compareEstablecimiento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEstablecimiento(entity1, entity2);
        const compareResult2 = service.compareEstablecimiento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
