import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';

import { PasswordResetInit } from './password-reset-init.service';

@Component({
    selector: 'jhi-password-reset-init',
    templateUrl: './password-reset-init.component.html'
})
export class PasswordResetInitComponent implements OnInit, AfterViewInit {
    error: string;
    errorEmailNotExists: string;
    resetAccount: any;
    success: string;

    constructor(
        private passwordResetInit: PasswordResetInit,
        private elementRef: ElementRef,
        private renderer: Renderer
    ) {
    }

    ngOnInit() {
        this.resetAccount = {};
    }

    ngAfterViewInit() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#email'), 'focus', []);
    }

    requestReset() {
        this.error = null;
        this.errorEmailNotExists = null;
        console.log("EMAIL LOGIN" + this.resetAccount.email);
        this.passwordResetInit.save(this.resetAccount.email).subscribe(() => {
            this.success = 'OK';
        }, (response) => {
            this.success = null;

            if (response.status === 400 && response.data === 'email address not registered') {
                this.errorEmailNotExists = 'ERROR';
            } else {
                this.error = 'ERROR';
            }
        });
    }
}
