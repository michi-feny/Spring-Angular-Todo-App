import { CommonModule } from '@angular/common';
import { Component, Input, TemplateRef } from '@angular/core';
import { NgbAccordionModule } from '@ng-bootstrap/ng-bootstrap';

interface AccordionItem {
  id: any;
}

@Component({
    selector: 'ngbd-accordion-static',
    imports: [NgbAccordionModule, CommonModule],
    templateUrl: './accordion-static.html',
    styleUrl: './accordion-static.css',
})
export class NgbdAccordionStatic {
    @Input() items: AccordionItem[] = [];
    @Input() headerTemplate: TemplateRef<any> | null = null;
    @Input() bodyTemplate: TemplateRef<any> | null = null;
}
