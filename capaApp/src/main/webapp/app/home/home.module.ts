import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CapaAppSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import {DashboardComponent} from "../dashboard/dashboard.component";

@NgModule({
    imports: [
        CapaAppSharedModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapaAppHomeModule {}
