import { BaseService } from './base.service';

// 1. Create a dummy subclass just for testing
class TestBaseService extends BaseService {
  // Expose the protected method so we can test it
  public testBuildUrl(resource: string): string {
    return this.buildUrl(resource);
  }
}

describe('BaseService', () => {
  let service: TestBaseService;

  beforeEach(() => {
    // 2. Instantiate the test subclass instead of the abstract class
    service = new TestBaseService();
  });

  it('should create an instance', () => {
    expect(service).toBeTruthy();
  });

  it('should build the correct URL', () => {
    // 3. Test the logic inside your abstract class
    const url = service.testBuildUrl('api/v1/test');
    
    // Adjust this expectation based on what environment.apiUrl is in your test environment
    expect(url).toContain('api/v1/test'); 
  });
});
