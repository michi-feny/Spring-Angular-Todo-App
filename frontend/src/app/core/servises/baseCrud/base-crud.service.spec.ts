import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing'; // Needed to mock HTTP calls
import { Injectable } from '@angular/core';

import { BaseCrudService } from './base-crud.service';

// 1. Create a concrete dummy class that extends your abstract class
@Injectable()
class TestCrudService extends BaseCrudService<any, number> {
  constructor() {
    super('test-resource'); // Provide a fake resource path for testing
  }
}

describe('BaseCrudService', () => {
  let service: TestCrudService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], // Provide the mock HttpClient
      providers: [TestCrudService]        // Provide our dummy class
    });
    // 2. Inject the dummy class instead of the abstract one
    service = TestBed.inject(TestCrudService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
