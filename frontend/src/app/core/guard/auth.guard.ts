import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { map, take } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { selectIsAuthenticated } from '../../features/auth/store/auth.selector';

export const authGuard: CanActivateFn = (): Observable<boolean> => {
    
    const store = inject(Store);
    const router = inject(Router);

    return store.select(selectIsAuthenticated).pipe(
        take(1), 
        map(isAuthenticated => {
            if (!isAuthenticated) {
                router.navigate(['/auth/login']); 
                return false;
            }
            
            return true;
        })
    );
};