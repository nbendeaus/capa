import { Injectable } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

import { Principal } from '../auth/principal.service';
import { AuthServerProvider } from '../auth/auth-jwt.service';
import { JhiTrackerService } from '../tracker/tracker.service';
import {Router} from "@angular/router";

@Injectable()
export class LoginService {

    constructor(
        private languageService: JhiLanguageService,
        private principal: Principal,
        private trackerService: JhiTrackerService,
        private authServerProvider: AuthServerProvider,
        private router : Router
    ) {}

    login(credentials, callback?) {
        const cb = callback || function() {};

        return new Promise((resolve, reject) => {
            this.authServerProvider.login(credentials).subscribe((data) => {
                this.principal.identity(true).then((account) => {
                    // After the login the language will be changed to
                    // the language selected by the user during his registration
                    console.log("ACCOUNT: " + account);
                    if (account !== null) {
                        this.languageService.changeLanguage(account.langKey);
                    }
                    this.trackerService.sendActivity();
                    resolve(data);
                    this.router.navigate(['']);
                });
                return cb();
            }, (err) => {
                this.logout();
                reject(err);
                return cb(err);
            });
        });
    }

    loginWithToken(jwt, rememberMe) {
        return this.authServerProvider.loginWithToken(jwt, rememberMe);
    }

    logout() {
        this.authServerProvider.logout().subscribe();
        this.principal.authenticate(null);
        this.router.navigate(['/login']);
    }
}
