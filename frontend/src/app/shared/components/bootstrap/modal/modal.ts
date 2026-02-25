import { CommonModule } from '@angular/common';
import { Component, inject, Input, TemplateRef, ViewChild, ViewEncapsulation } from '@angular/core';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';

@Component({
	  selector: 'ngbd-modal',
	  templateUrl: './modal.html',
    imports: [CommonModule],
	  encapsulation: ViewEncapsulation.None,
})

export class Modal {
	  private modalService = inject(NgbModal);

    @Input() titleTemplate!: TemplateRef<any>;
    @Input() bodyTemplate!: TemplateRef<any>;
    @Input() footerTemplate!: TemplateRef<any>;

    @ViewChild('content') modalContentRef!: TemplateRef<any>;

	  openModal(options?: NgbModalOptions) {
	  	  this.modalService.open(this.modalContentRef, options);
	  }

    closeModal(reason: string = 'close Modal') {
        this.modalService.dismissAll(reason);
    }
}