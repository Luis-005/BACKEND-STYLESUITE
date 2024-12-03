import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGaleriaImagen } from '../galeria-imagen.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../galeria-imagen.test-samples';

import { GaleriaImagenService } from './galeria-imagen.service';

const requireRestSample: IGaleriaImagen = {
  ...sampleWithRequiredData,
};

describe('GaleriaImagen Service', () => {
  let service: GaleriaImagenService;
  let httpMock: HttpTestingController;
  let expectedResult: IGaleriaImagen | IGaleriaImagen[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GaleriaImagenService);
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

    it('should create a GaleriaImagen', () => {
      const galeriaImagen = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(galeriaImagen).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GaleriaImagen', () => {
      const galeriaImagen = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(galeriaImagen).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GaleriaImagen', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GaleriaImagen', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GaleriaImagen', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGaleriaImagenToCollectionIfMissing', () => {
      it('should add a GaleriaImagen to an empty array', () => {
        const galeriaImagen: IGaleriaImagen = sampleWithRequiredData;
        expectedResult = service.addGaleriaImagenToCollectionIfMissing([], galeriaImagen);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(galeriaImagen);
      });

      it('should not add a GaleriaImagen to an array that contains it', () => {
        const galeriaImagen: IGaleriaImagen = sampleWithRequiredData;
        const galeriaImagenCollection: IGaleriaImagen[] = [
          {
            ...galeriaImagen,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGaleriaImagenToCollectionIfMissing(galeriaImagenCollection, galeriaImagen);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GaleriaImagen to an array that doesn't contain it", () => {
        const galeriaImagen: IGaleriaImagen = sampleWithRequiredData;
        const galeriaImagenCollection: IGaleriaImagen[] = [sampleWithPartialData];
        expectedResult = service.addGaleriaImagenToCollectionIfMissing(galeriaImagenCollection, galeriaImagen);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(galeriaImagen);
      });

      it('should add only unique GaleriaImagen to an array', () => {
        const galeriaImagenArray: IGaleriaImagen[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const galeriaImagenCollection: IGaleriaImagen[] = [sampleWithRequiredData];
        expectedResult = service.addGaleriaImagenToCollectionIfMissing(galeriaImagenCollection, ...galeriaImagenArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const galeriaImagen: IGaleriaImagen = sampleWithRequiredData;
        const galeriaImagen2: IGaleriaImagen = sampleWithPartialData;
        expectedResult = service.addGaleriaImagenToCollectionIfMissing([], galeriaImagen, galeriaImagen2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(galeriaImagen);
        expect(expectedResult).toContain(galeriaImagen2);
      });

      it('should accept null and undefined values', () => {
        const galeriaImagen: IGaleriaImagen = sampleWithRequiredData;
        expectedResult = service.addGaleriaImagenToCollectionIfMissing([], null, galeriaImagen, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(galeriaImagen);
      });

      it('should return initial array if no GaleriaImagen is added', () => {
        const galeriaImagenCollection: IGaleriaImagen[] = [sampleWithRequiredData];
        expectedResult = service.addGaleriaImagenToCollectionIfMissing(galeriaImagenCollection, undefined, null);
        expect(expectedResult).toEqual(galeriaImagenCollection);
      });
    });

    describe('compareGaleriaImagen', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGaleriaImagen(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGaleriaImagen(entity1, entity2);
        const compareResult2 = service.compareGaleriaImagen(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGaleriaImagen(entity1, entity2);
        const compareResult2 = service.compareGaleriaImagen(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGaleriaImagen(entity1, entity2);
        const compareResult2 = service.compareGaleriaImagen(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
