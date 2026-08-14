import { Component, inject } from '@angular/core';
import { NavDropdown } from '../../../shared/components/bootstrap/dropdown/nav-dropdown/nav-dropdown';
import LinkOption from '../../../types/LinkOption';
import { Store } from '@ngrx/store';
import { selectIsAuthenticated } from '../../../features/auth/store/auth.selector';
import { map, Observable, take } from 'rxjs';
import { AuthState } from '../../../features/auth/store/auth.models';
import { loadTokenFromStorage, logout } from '../../../features/auth/store/auth.actions';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-header', 
  imports: [NavDropdown, RouterLink, CommonModule, TranslateModule],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
    public readonly isLoggedIn$: Observable<boolean>
    public readonly dropdownOptions$: Observable<LinkOption[]>;
    private store = inject(Store<AuthState>);
    //private translate = inject(TranslateService);

    constructor() {
        this.store.dispatch(loadTokenFromStorage());

        this.isLoggedIn$ = this.store.select(selectIsAuthenticated);

        this.dropdownOptions$ = this.isLoggedIn$.pipe(
            map(isLoggedIn => {
                const options: LinkOption[] = [];

                if (isLoggedIn) {
                    options.push({ 
                        name: 'NAV.ACCOUNT.LOGOUT', 
                        action: () => this.store.dispatch(logout()) 
                    });
                   // options.push(name:'fff', href: "todos")
                } else {
                    options.push({ name: 'NAV.ACCOUNT.LOGIN', href: "auth/login" });
                    options.push({ name: 'NAV.ACCOUNT.SIGNUP', href: "auth/signup" });
                }

                return options;
            })
        );
    }
} 
