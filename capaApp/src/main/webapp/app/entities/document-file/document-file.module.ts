import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CapaAppSharedModule } from '../../shared';
import {
    DocumentFileService,
    DocumentFilePopupService,
    DocumentFileComponent,
    DocumentFileDetailComponent,
    DocumentFileDialogComponent,
    DocumentFilePopupComponent,
    DocumentFileDeletePopupComponent,
    DocumentFileDeleteDialogComponent,
    documentFileRoute,
    documentFilePopupRoute,
} from './';
import {DocumentFileEditComponent} from "./document-file-edit.component";

const ENTITY_STATES = [
    ...documentFileRoute,
    ...documentFilePopupRoute,
];

@NgModule({
    imports: [
        CapaAppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DocumentFileComponent,
        DocumentFileDetailComponent,
        DocumentFileDialogComponent,
        DocumentFileDeleteDialogComponent,
        DocumentFilePopupComponent,
        DocumentFileDeletePopupComponent,
        DocumentFileEditComponent,
    ],
    entryComponents: [
        DocumentFileComponent,
        DocumentFileDialogComponent,
        DocumentFilePopupComponent,
        DocumentFileDeleteDialogComponent,
        DocumentFileDeletePopupComponent,
    ],
    providers: [
        DocumentFileService,
        DocumentFilePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapaAppDocumentFileModule {}
