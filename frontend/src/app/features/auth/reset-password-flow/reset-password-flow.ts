import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../core/servises/AuthService';
import { CommonModule } from '@angular/common';
import { Store } from '@ngrx/store';
import { FlashMessageState, MessageType } from '../../../store/fleshMessage/fleshMessage.models';
import { addFlashMessage } from '../../../store/fleshMessage/fleshMessage.actions';
import { HttpErrorResponse } from '@angular/common/http';
import { TranslateModule } from '@ngx-translate/core';
import { FormHelper } from '../../../shared/validator/FormHelper';
import { FormGroup } from '@angular/forms';
import { ResetPasswordForm } from '../reset-password/reset-password';
import { ResetPasswordForm as ResetPasswordFormType } from '../../../types/forms/auth-forms';

@Component({
    selector: 'app-reset-password-flow',
    imports: [CommonModule, ResetPasswordForm, TranslateModule],
    templateUrl: './reset-password-flow.html',
    styleUrl: './reset-password-flow.css',
})
export class ResetPasswordFlow implements OnInit {

    private store: Store<FlashMessageState> = inject(Store<FlashMessageState>);
    private authService: AuthService = inject(AuthService);
    private route: ActivatedRoute = inject(ActivatedRoute);
    private router: Router = inject(Router);

    public errorMessage = signal<string | null>(null);
    public userId = signal<number | null>(null);
    public token = signal<string | null>(null);
    public isLoading = signal(false);

    ngOnInit(): void {
        const urlToken = this.route.snapshot.queryParamMap.get('token');

        if (!urlToken) {
            this.errorMessage.set('RESET_PASSWORD.INAVLID_RESET_TOKEN');
            return;
        }

        this.token.set(urlToken);

        this.authService.validateResetToken({ token: urlToken }).subscribe({
              next: (response) => {
                  this.userId.set(response.data?.userId ?? null); 
                  this.errorMessage.set(null);
              },
              error: (err: HttpErrorResponse) => {
                    if(err.status === 401 || err.status === 410) {
                        this.errorMessage.set(err.error.detail);
                    }
              }
        });
    }

    handleResetSubmit(resetPasswordForm: FormGroup<ResetPasswordFormType>): void {
        const currentUserId = this.userId();
        const currentToken = this.token();

        if (currentUserId === null || currentToken === null) {
            this.errorMessage.set('RESET_PASSWORD.INAVLID_RESET_TOKEN');
            return;
        }

        this.isLoading.set(true);

        const request = {
            ...resetPasswordForm.getRawValue(),
            token: currentToken,
            userID: currentUserId
        };

        this.authService.resetPassword(request).subscribe({
            next: (response) => {
                this.isLoading.set(false);

                this.store.dispatch(addFlashMessage({
                    text: response.message,
                    messageType: MessageType.Success,
                    timeout: 6000,
                    keepAfterNavigation: true
                }));

                this.router.navigate(['/auth/login']);
            },
            error: (err: HttpErrorResponse) => {
                this.isLoading.set(false);
                if(err.status === 401 || err.status === 410) {
                    this.errorMessage = err.error.detail;
                }
                else if(err.status === 422) {
                    FormHelper.setFormErrors(resetPasswordForm, err.error.errors);
                }
            }
        });
    }
}
