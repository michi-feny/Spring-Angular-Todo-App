import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, TemplateRef } from '@angular/core';
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

    @Output() shown = new EventEmitter<string | number>();
    @Output() hidden = new EventEmitter<string | number>();
    @Output() itemClick = new EventEmitter<string | number>();

    onItemClick(id: string | number): void {
      this.itemClick.emit(id);
    }
}
