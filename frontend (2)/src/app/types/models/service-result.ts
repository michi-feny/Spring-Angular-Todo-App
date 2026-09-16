import { BusinessViolation } from './business-violation';

export interface ServiceResult<T> {
  status: 'SUCCESS' | 'REJECTED';
  value: T;
  violations: BusinessViolation[];
}
