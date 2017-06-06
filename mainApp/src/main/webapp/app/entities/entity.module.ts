import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CapaLocationModule } from './location/location.module';
import { CapaEmployeeModule } from './employee/employee.module';
import { CapaProjectModule } from './project/project.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CapaLocationModule,
        CapaEmployeeModule,
        CapaProjectModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapaEntityModule {}
