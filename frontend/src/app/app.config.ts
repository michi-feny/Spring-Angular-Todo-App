import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZonelessChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideState, provideStore } from '@ngrx/store';
import { provideEffects } from '@ngrx/effects';
import { authFeatureKey, authReducer } from './features/auth/store/auth.reducer';
import { AuthEffects } from './features/auth/store/auth.effects';
import { tokenInterceptor } from './shared/interceptors/token.interceptor';
import { fleshMessageFeatureKey, fleshMessageReducer } from './store/fleshMessage/fleshMessage.reducer';
import { FleshMessageEffects } from './store/fleshMessage/fleshMessage.effects';
import { refreshInterceptor } from './shared/interceptors/refresh.interceptor';
import { provideTranslateService } from '@ngx-translate/core';
import { provideTranslateHttpLoader } from '@ngx-translate/http-loader';

export const appConfig: ApplicationConfig = {
    providers: [
        provideBrowserGlobalErrorListeners(),
        provideZonelessChangeDetection(),
        provideRouter(routes),
        provideHttpClient(withInterceptors([tokenInterceptor, refreshInterceptor])),
        provideStore(),
        provideState(authFeatureKey, authReducer),
        provideState(fleshMessageFeatureKey, fleshMessageReducer),
        provideEffects([AuthEffects, FleshMessageEffects]),
        provideTranslateService({
            loader: provideTranslateHttpLoader({
                prefix: '/assets/i18n/',
                suffix: '.json'
            }),
            fallbackLang: 'en',
            lang: 'en'
        })
    ]
};
