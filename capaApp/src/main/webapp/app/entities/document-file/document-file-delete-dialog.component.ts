import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { DocumentFile } from './document-file.model';
import { DocumentFilePopupService } from './document-file-popup.service';
import { DocumentFileService } from './document-file.service';

@Component({
    selector: 'jhi-document-file-delete-dialog',
    templateUrl: './document-file-delete-dialog.component.html'
})
export class DocumentFileDeleteDialogComponent {

    documentFile: DocumentFile;

    constructor(
        private documentFileService: DocumentFileService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentFileService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'documentFileListModification',
                content: 'Deleted an documentFile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-document-file-delete-popup',
    template: ''
})
export class DocumentFileDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private documentFilePopupService: DocumentFilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.documentFilePopupService
                .open(DocumentFileDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
