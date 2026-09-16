import { CommonModule } from '@angular/common';

import { Component, Input, TemplateRef, inject, OnInit } from '@angular/core';
import { NgbAccordionModule } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormArray, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

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
