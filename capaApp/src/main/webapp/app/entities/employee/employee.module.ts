import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CapaAppSharedModule } from '../../shared';
import {
    EmployeeService,
    EmployeePopupService,
    EmployeeComponent,
    EmployeeDetailComponent,
    EmployeeEditComponent,
    EmployeeDialogComponent,
    EmployeePopupComponent,
    EmployeeDeletePopupComponent,
    EmployeeDeleteDialogComponent,
    employeeRoute,
    employeePopupRoute,
} from './';


const ENTITY_STATES = [
    ...employeeRoute,
    ...employeePopupRoute,
];

@NgModule({
    imports: [
        CapaAppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EmployeeComponent,
        EmployeeDetailComponent,
        EmployeeDialogComponent,
        EmployeeDeleteDialogComponent,
        EmployeePopupComponent,
        EmployeeDeletePopupComponent,
        EmployeeEditComponent,
    ],
    entryComponents: [
        EmployeeComponent,
        EmployeeDialogComponent,
        EmployeePopupComponent,
        EmployeeDeleteDialogComponent,
        EmployeeDeletePopupComponent,
    ],
    providers: [
        EmployeeService,
        EmployeePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapaAppEmployeeModule {}
