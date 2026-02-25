import { Component, Input } from '@angular/core';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import LinkOption from '../../../../../types/LinkOption';
import { RouterLink } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

@Component({
    selector: 'ngbd-dropdown-navbar',
    imports: [NgbDropdownModule, RouterLink, TranslateModule],
    templateUrl: './nav-dropdown.html',
    styleUrl: './nav-dropdown.css',
})
export class NavDropdown {
    @Input() name:string = '';
    @Input() options:LinkOption[] = [];
}