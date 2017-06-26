import { Component, OnInit, OnDestroy } from '@angular/core';
import {ActivatedRoute, Route, Router} from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import {AlertService, EventManager} from 'ng-jhipster';

import { User, UserService } from '../../shared';
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-user-mgmt-edit',
    templateUrl: './user-management-edit.component.html'
})
export class UserMgmtEditComponent implements OnInit, OnDestroy {

    user: User;
    languages: any[];
    authorities: any[];
    isSaving: Boolean;
    isNewUser: Boolean;
    routeSub: any;
    private subscription: Subscription;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute,
        private languageHelper: JhiLanguageHelper,
        private eventManager: EventManager,
        private alertService: AlertService,
        private router : Router,
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = [];
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['login']) {
                this.load(params['login'])
            } else {
                this.load();
            }
        });
        this.userService.authorities().subscribe((authorities) => {
            this.authorities = authorities;
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    load(login?: string) {
        if (login) {
            this.userService.find(login).subscribe((user) => {
                this.user = user;
            });
        } else {
            this.user = new User();
        }
    }


    save() {
        this.isSaving = true;
        if (this.user.id !== null) {
            console.log("Update: " + this.user.login);
            this.userService.update(this.user).subscribe((response) => this.onSaveSuccess(response), (response) => this.onSaveError(response.json()));
        } else {
            console.log("Create: " + this.user.login);
            this.userService.create(this.user).subscribe((response) => this.onSaveSuccess(response), (response) => this.onSaveError(response.json()));
        }
    }

    back() {
        if (this.user.id !== null) {
            this.router.navigate(['/dashboard/user-management'])
        } else {
            this.router.navigate(['/dashboard/user-management'])
        }
    }

    private onSaveSuccess(result) {
        this.eventManager.broadcast({ name: 'userListModification', content: 'OK' });
        this.isSaving = false;
        this.alertService.success("User successfully updated or created", null, null);
    }

    private onSaveError(error) {
        this.isSaving = false;
        this.alertService.error(error.error, error.message, null);
    }

    ngOnDestroy() {
    }

}
