import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CapaAppEmployeeModule } from './employee/employee.module';
import { CapaAppProjectModule } from './project/project.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CapaAppEmployeeModule,
        CapaAppProjectModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapaAppEntityModule {}
