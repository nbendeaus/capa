import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { errorRoute} from './';

import {FullLayoutComponent} from './layout/full-layout.component';

import {DashboardComponent} from "../dashboard/dashboard.component";
import {JhiLoginModalComponent} from "../shared/login/login.component";
import {UserRouteAccessService} from "../shared/index";
import {adminState} from "../admin/admin.route";
import {employeePopupRoute, employeeRoute} from "../entities/employee/employee.route";

const ROUTES = [
    {
        path: '',
        redirectTo: '/dashboard',
        pathMatch: 'full',
    },
    {
        path: '',
        component: FullLayoutComponent,
        data: {
            pageTitle: 'Home',
            title: ''
        },
        children: [
            {
                path: 'dashboard',
                component: DashboardComponent, canActivate: [UserRouteAccessService],
                data: {
                    authorities: ['ROLE_USER', 'ROLE_ADMIN'],
                    pageTitle: 'dashboard.title',
                    title: 'Dashboard'
                },
            },

            ...adminState,
            ...employeeRoute,
        ]
    },
    {
        path: 'login',
        component: JhiLoginModalComponent,
        data: {
            pageTitle: 'login.title',
            title: 'Login'
        },
    },

        ...errorRoute,

    // {
    //     path: '**',
    //     redirectTo: '404'
    // },
];

@NgModule({
    imports: [
        RouterModule.forRoot(ROUTES, { useHash: false }),
        //RouterModule.forChild(ROUTES)
    ],
    exports: [
        RouterModule
    ]
})
export class LayoutRoutingModule {}
