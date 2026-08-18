import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Injectable } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { BasePersonRelatedCrudService } from './base-person-related-crud.service';

// 1. Create a concrete dummy class to wrap the abstract class
@Injectable()
class TestPersonRelatedCrudService extends BasePersonRelatedCrudService<any, any, number> {
  constructor() {
    super('test-related-resource');
  }
}

describe('BasePersonRelatedCrudService', () => {
  let service: TestPersonRelatedCrudService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], // Provide the mock HttpClient
      providers: [TestPersonRelatedCrudService] // Provide the dummy class
    });
    // 2. Inject the dummy class instead of the abstract one
    service = TestBed.inject(TestPersonRelatedCrudService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
