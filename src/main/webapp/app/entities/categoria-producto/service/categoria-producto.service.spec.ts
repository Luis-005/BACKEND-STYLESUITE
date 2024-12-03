import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICategoriaProducto } from '../categoria-producto.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../categoria-producto.test-samples';

import { CategoriaProductoService } from './categoria-producto.service';

const requireRestSample: ICategoriaProducto = {
  ...sampleWithRequiredData,
};

describe('CategoriaProducto Service', () => {
  let service: CategoriaProductoService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategoriaProducto | ICategoriaProducto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CategoriaProductoService);
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

    it('should create a CategoriaProducto', () => {
      const categoriaProducto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categoriaProducto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategoriaProducto', () => {
      const categoriaProducto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categoriaProducto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategoriaProducto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategoriaProducto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategoriaProducto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategoriaProductoToCollectionIfMissing', () => {
      it('should add a CategoriaProducto to an empty array', () => {
        const categoriaProducto: ICategoriaProducto = sampleWithRequiredData;
        expectedResult = service.addCategoriaProductoToCollectionIfMissing([], categoriaProducto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoriaProducto);
      });

      it('should not add a CategoriaProducto to an array that contains it', () => {
        const categoriaProducto: ICategoriaProducto = sampleWithRequiredData;
        const categoriaProductoCollection: ICategoriaProducto[] = [
          {
            ...categoriaProducto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategoriaProductoToCollectionIfMissing(categoriaProductoCollection, categoriaProducto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategoriaProducto to an array that doesn't contain it", () => {
        const categoriaProducto: ICategoriaProducto = sampleWithRequiredData;
        const categoriaProductoCollection: ICategoriaProducto[] = [sampleWithPartialData];
        expectedResult = service.addCategoriaProductoToCollectionIfMissing(categoriaProductoCollection, categoriaProducto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoriaProducto);
      });

      it('should add only unique CategoriaProducto to an array', () => {
        const categoriaProductoArray: ICategoriaProducto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categoriaProductoCollection: ICategoriaProducto[] = [sampleWithRequiredData];
        expectedResult = service.addCategoriaProductoToCollectionIfMissing(categoriaProductoCollection, ...categoriaProductoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categoriaProducto: ICategoriaProducto = sampleWithRequiredData;
        const categoriaProducto2: ICategoriaProducto = sampleWithPartialData;
        expectedResult = service.addCategoriaProductoToCollectionIfMissing([], categoriaProducto, categoriaProducto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoriaProducto);
        expect(expectedResult).toContain(categoriaProducto2);
      });

      it('should accept null and undefined values', () => {
        const categoriaProducto: ICategoriaProducto = sampleWithRequiredData;
        expectedResult = service.addCategoriaProductoToCollectionIfMissing([], null, categoriaProducto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoriaProducto);
      });

      it('should return initial array if no CategoriaProducto is added', () => {
        const categoriaProductoCollection: ICategoriaProducto[] = [sampleWithRequiredData];
        expectedResult = service.addCategoriaProductoToCollectionIfMissing(categoriaProductoCollection, undefined, null);
        expect(expectedResult).toEqual(categoriaProductoCollection);
      });
    });

    describe('compareCategoriaProducto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategoriaProducto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCategoriaProducto(entity1, entity2);
        const compareResult2 = service.compareCategoriaProducto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCategoriaProducto(entity1, entity2);
        const compareResult2 = service.compareCategoriaProducto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCategoriaProducto(entity1, entity2);
        const compareResult2 = service.compareCategoriaProducto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
