import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , DataUtils } from 'ng-jhipster';

import { DocumentFile } from './document-file.model';
import { DocumentFileService } from './document-file.service';

@Component({
    selector: 'jhi-document-file-detail',
    templateUrl: './document-file-detail.component.html'
})
export class DocumentFileDetailComponent implements OnInit, OnDestroy {

    documentFile: DocumentFile;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private dataUtils: DataUtils,
        private documentFileService: DocumentFileService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDocumentFiles();
    }

    load(id) {
        this.documentFileService.find(id).subscribe((documentFile) => {
            this.documentFile = documentFile;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDocumentFiles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'documentFileListModification',
            (response) => this.load(this.documentFile.id)
        );
    }
}
