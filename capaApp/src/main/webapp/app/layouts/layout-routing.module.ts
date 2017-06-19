import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { navbarRoute } from '../app.route';
import { errorRoute } from './';
import { LoginComponent} from '../login'

const LAYOUT_ROUTES = [
    navbarRoute,
    ...errorRoute,

];

const ROUTES = [
    {path: 'login', component: LoginComponent}
];

@NgModule({
    imports: [
        RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true }),
        RouterModule.forChild(ROUTES)
    ],
    exports: [
        RouterModule
    ]
})
export class LayoutRoutingModule {}
