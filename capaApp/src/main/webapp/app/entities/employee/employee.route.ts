import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {PaginationUtil} from 'ng-jhipster';

import {EmployeeComponent} from './employee.component';
import {EmployeeDetailComponent} from './employee-detail.component';
import {EmployeePopupComponent} from './employee-dialog.component';
import {EmployeeDeletePopupComponent} from './employee-delete-dialog.component';
import {EmployeeEditComponent} from "./employee-edit.component";

export const employeeRoute: Routes = [
    {
        path: 'employee',
        component: EmployeeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'employee.home.title',
            title: 'Employees',

        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'employee-new',
        component: EmployeeEditComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'employee.home.title',
            title: 'Employees - New',
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'employee/:id',
        component: EmployeeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'employee.home.title',
            title: 'Employees - Detail',
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee/:id/edit',
        component: EmployeeEditComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'employee.home.title',
            title: 'Employees - Edit',
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee/:id/delete',
        component: EmployeeDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'capaApp.employee.home.title',
            title: 'Employees',
        },
        canActivate: [UserRouteAccessService],
    }
];

export const employeePopupRoute: Routes = [
    {
        path: 'employee-new',
        component: EmployeePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'capaApp.employee.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'employee/:id/edit',
        component: EmployeePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'capaApp.employee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee/:id/delete',
        component: EmployeeDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'capaApp.employee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
