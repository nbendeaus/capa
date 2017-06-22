import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { navbarRoute } from '../app.route';
import { errorRoute} from './';

import {FullLayoutComponent} from './layout/full-layout.component';

import {DashboardComponent} from "../dashboard/dashboard.component";
import {JhiLoginModalComponent} from "../shared/login/login.component";
import {UserRouteAccessService} from "../shared/index";
import {userMgmtRoute} from "../admin/user-management/user-management.route";
import {adminState} from "../admin/admin.route";

const LAYOUT_ROUTES = [
    navbarRoute,


];

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
            title: 'Capa'
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
                children: [
                    ...adminState,
                ]
            },
        ]
    },
    {
        path: 'login',
        component: JhiLoginModalComponent,
    },

        ...errorRoute,

    {
        path: '**',
        redirectTo: '404'
    },
];

@NgModule({
    imports: [
        RouterModule.forRoot(ROUTES, { useHash: true }),
        //RouterModule.forChild(ROUTES)
    ],
    exports: [
        RouterModule
    ]
})
export class LayoutRoutingModule {}
