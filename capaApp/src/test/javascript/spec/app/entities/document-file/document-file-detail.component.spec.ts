import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CapaAppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DocumentFileDetailComponent } from '../../../../../../main/webapp/app/entities/document-file/document-file-detail.component';
import { DocumentFileService } from '../../../../../../main/webapp/app/entities/document-file/document-file.service';
import { DocumentFile } from '../../../../../../main/webapp/app/entities/document-file/document-file.model';

describe('Component Tests', () => {

    describe('DocumentFile Management Detail Component', () => {
        let comp: DocumentFileDetailComponent;
        let fixture: ComponentFixture<DocumentFileDetailComponent>;
        let service: DocumentFileService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CapaAppTestModule],
                declarations: [DocumentFileDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DocumentFileService,
                    EventManager
                ]
            }).overrideComponent(DocumentFileDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentFileDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentFileService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DocumentFile(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.documentFile).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
