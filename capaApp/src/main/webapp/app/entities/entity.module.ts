import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';


import { CapaAppEmployeeModule } from './employee/employee.module';
import { CapaAppDocumentFileModule } from './document-file/document-file.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CapaAppEmployeeModule,
        CapaAppDocumentFileModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapaAppEntityModule {}
