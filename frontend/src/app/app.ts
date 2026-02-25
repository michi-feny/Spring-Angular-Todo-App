import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './core/layout/header/header';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, Header],
    templateUrl: './app.html',
    styleUrl: './app.css',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App {
    private translate = inject(TranslateService);

    constructor() {
        const supportedLangs = ['de', 'en'];
        this.translate.addLangs(supportedLangs);

        this.translate.setFallbackLang('en');
        
        const savedLang = localStorage.getItem('user_lang');
    
        const browserLang = this.translate.getBrowserLang() || 'en';

        const finalLang = savedLang || (supportedLangs.includes(browserLang) ? browserLang : 'en');
    
        this.translate.use(finalLang);
    }
}
