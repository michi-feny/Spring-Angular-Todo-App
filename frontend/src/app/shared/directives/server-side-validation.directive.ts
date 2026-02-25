import { Directive, inject, Input, OnInit, DestroyRef } from '@angular/core';
import { FormGroupDirective } from '@angular/forms';
import { Store, MemoizedSelector } from '@ngrx/store';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormHelper } from '../validator/FormHelper';

@Directive({
  selector: '[appServerSideValidation]',
  standalone: true
})
export class ServerSideValidationDirective implements OnInit {
    private store = inject(Store);
    private destroyRef = inject(DestroyRef);
    private formGroupDirective = inject(FormGroupDirective);

    @Input('appServerSideValidation') errorSelector!: MemoizedSelector<any, any>;

    ngOnInit(): void {
        if (!this.errorSelector) {
            console.warn('ServerSideValidationDirective: No error selector provided.');
            return;
        }

        this.store.select(this.errorSelector)
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe(error => {
                if (error && error.error?.status === 422) {
                    const errors = error.error.errors;
                    FormHelper.setFormErrors(this.formGroupDirective.form, errors);
                }
            });
    }
}