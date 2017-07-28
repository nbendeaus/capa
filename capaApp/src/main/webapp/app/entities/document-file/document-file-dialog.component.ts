import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, DataUtils } from 'ng-jhipster';

import { DocumentFile } from './document-file.model';
import { DocumentFilePopupService } from './document-file-popup.service';
import { DocumentFileService } from './document-file.service';

@Component({
    selector: 'jhi-document-file-dialog',
    templateUrl: './document-file-dialog.component.html'
})
export class DocumentFileDialogComponent implements OnInit {

    documentFile: DocumentFile;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private documentFileService: DocumentFileService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, documentFile, field, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                documentFile[field] = base64Data;
                documentFile[`${field}ContentType`] = file.type;
            });
        }
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.documentFile.id !== undefined) {
            this.subscribeToSaveResponse(
                this.documentFileService.update(this.documentFile));
        } else {
            this.subscribeToSaveResponse(
                this.documentFileService.create(this.documentFile));
        }
    }

    private subscribeToSaveResponse(result: Observable<DocumentFile>) {
        result.subscribe((res: DocumentFile) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DocumentFile) {
        this.eventManager.broadcast({ name: 'documentFileListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-document-file-popup',
    template: ''
})
export class DocumentFilePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private documentFilePopupService: DocumentFilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.documentFilePopupService
                    .open(DocumentFileDialogComponent, params['id']);
            } else {
                this.modalRef = this.documentFilePopupService
                    .open(DocumentFileDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
