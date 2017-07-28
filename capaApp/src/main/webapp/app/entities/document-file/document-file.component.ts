import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService, DataUtils } from 'ng-jhipster';
import {  FileUploader } from 'ng2-file-upload/ng2-file-upload';

import { DocumentFile } from './document-file.model';
import { DocumentFileService } from './document-file.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { JhiConfigurationService } from '../../admin/configuration/configuration.service';

@Component({
    selector: 'jhi-document-file',
    templateUrl: './document-file.component.html'
})
export class DocumentFileComponent implements OnInit, OnDestroy {

    documentFiles: DocumentFile[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;

    private resourceUrl = 'fileuploadservice/api/document-files';

    constructor(
        private documentFileService: DocumentFileService,
        private alertService: AlertService,
        private dataUtils: DataUtils,
        private eventManager: EventManager,
        private parseLinks: ParseLinks,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private uploader : FileUploader,
        private configService : JhiConfigurationService,
    ) {
        this.documentFiles = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }


    loadAll() {
        if (this.currentSearch) {
            this.documentFileService.search({
                query: this.currentSearch,
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            }).subscribe(
                (res: Response) => this.onSuccess(res.json(), res.headers),
                (res: Response) => this.onError(res.json())
            );
            return;
        }
        this.documentFileService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }

    reset() {
        this.page = 0;
        this.documentFiles = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.documentFiles = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch = '';
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.documentFiles = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = '_score';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDocumentFiles();
        if (this.configService.getEnv() == 'dev') {
            this.uploader = new FileUploader({url: 'http://localhost:8080/' + this.resourceUrl, itemAlias: 'csv'});
        } else {
            // TODO: change url for production
            this.uploader = new FileUploader({url: 'http://localhost:8080/' + this.resourceUrl, itemAlias: 'csv'});
        }
        this.uploader.onCompleteItem = (item:any, response:any, status:any, headers:any) => {
            console.log("FileUpload:uploaded:", item, status, response);
        };

    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DocumentFile) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInDocumentFiles() {
        this.eventSubscriber = this.eventManager.subscribe('documentFileListModification', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.documentFiles.push(data[i]);
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
