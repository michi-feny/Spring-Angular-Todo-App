import { environment } from "../../../../environments/environment";

export abstract class BaseService {
// Example base URL builder
  protected buildUrl(resource: string): string {
    const baseUrl = environment.apiUrl || 'http://localhost:8080';
    return `${baseUrl}/${resource}`;
  }
}
