import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DocumentFile } from './document-file.model';
import { DocumentFileService } from './document-file.service';
@Injectable()
export class DocumentFilePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private documentFileService: DocumentFileService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.documentFileService.find(id).subscribe((documentFile) => {
                this.documentFileModalRef(component, documentFile);
            });
        } else {
            return this.documentFileModalRef(component, new DocumentFile());
        }
    }

    documentFileModalRef(component: Component, documentFile: DocumentFile): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.documentFile = documentFile;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
