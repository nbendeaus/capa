import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DocumentFileComponent } from './document-file.component';
import { DocumentFileDetailComponent } from './document-file-detail.component';
import { DocumentFilePopupComponent } from './document-file-dialog.component';
import { DocumentFileDeletePopupComponent } from './document-file-delete-dialog.component';
import {DocumentFileEditComponent} from './document-file-edit.component';

import { Principal } from '../../shared';

export const documentFileRoute: Routes = [
    {
        path: 'document-file',
        component: DocumentFileComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'capaApp.documentFile.home.title',
            title: 'Uploaded File'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-file-new',
        component: DocumentFileEditComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'capaApp.documentFile.home.title',
            title: 'Upload New File'
        },
        canActivate: [UserRouteAccessService],
    },{
        path: 'document-file/:id',
        component: DocumentFileDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'capaApp.documentFile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },  {
        path: 'document-file/:id/edit',
        component: DocumentFileEditComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'capaApp.documentFile.home.title',
            title: 'Edit Uploaded File'
        },
        canActivate: [UserRouteAccessService],
    },{
        path: 'document-file/:id/delete',
        component: DocumentFileDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'capaApp.documentFile.home.title'
        },
        canActivate: [UserRouteAccessService],
    }
];

export const documentFilePopupRoute: Routes = [
    {
        path: 'document-file-new',
        component: DocumentFilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'capaApp.documentFile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document-file/:id/edit',
        component: DocumentFilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'capaApp.documentFile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document-file/:id/delete',
        component: DocumentFileDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'capaApp.documentFile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
