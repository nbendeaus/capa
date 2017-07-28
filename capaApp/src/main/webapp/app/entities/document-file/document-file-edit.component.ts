import {Component, OnDestroy, OnInit} from "@angular/core";
import {DocumentFile} from "./document-file.model";
import {ActivatedRoute, Router} from "@angular/router";
import {DocumentFileService} from "./document-file.service";
import {AlertService, EventManager} from "ng-jhipster";
import {Observable} from "rxjs/Observable";

@Component({
    selector: 'document-file-edit',
    templateUrl: './document-file-edit.component.html'
})
export class DocumentFileEditComponent implements OnInit {

    documentFile : DocumentFile;
    isSaving: boolean;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private router : Router,
        private eventManager: EventManager,
        private alertService: AlertService,
        private documentFileService : DocumentFileService,
    ){}

    ngOnInit(): void {
        this.isSaving = false;
        this.routeSub = this.route.params.subscribe((params) => {
            console.log("PARAMS"+ JSON.stringify(params));
            if (params['id']) {
                this.load(params['id'])
            } else {
                this.load();
            }
        });
    }

    load(id?: number) {
            console.log("LOAD: " + id);
        if (id) {
            this.documentFileService.find(id).subscribe((documentFile) => {
                this.documentFile = documentFile;
            });
        } else {
            this.documentFile = new DocumentFile();
        }
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


    back() {
       this.router.navigate(['/document-file']);
    }

    private subscribeToSaveResponse(result: Observable<DocumentFile>) {
        result.subscribe((res: DocumentFile) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DocumentFile) {
        this.eventManager.broadcast({ name: 'documentFileListModification', content: 'OK'});
        this.isSaving = false;
        this.alertService.success("DocumentFile successfully updated or created", null, null);
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
