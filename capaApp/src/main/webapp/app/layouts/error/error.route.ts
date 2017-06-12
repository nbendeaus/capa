import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ErrorComponent } from './error.component';
import {P404Component} from "./404.component";
import {P500Component} from "./500.component";

export const errorRoute: Routes = [
    {
        path: 'error',
        component: ErrorComponent,
        data: {
            authorities: [],
            pageTitle: 'error.title'
        },
    },
    {
        path: 'accessdenied',
        component: ErrorComponent,
        data: {
            authorities: [],
            pageTitle: 'error.title',
            error403: true
        },
    },
    {
        path: '404',
        component: P404Component,
        data: {
            title: 'Page 404'
        }
    },
    {
        path: '500',
        component: P500Component,
        data: {
            title: 'Page 500'
        }
    },
];
