import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DocumentFile } from './document-file.model';

@Injectable()
export class DocumentFileService {

    private resourceUrl = 'fileuploadservice/api/document-files';
    private resourceSearchUrl = 'fileuploadservice/api/_search/document-files';

    constructor(private http: Http) { }

    create(documentFile: DocumentFile): Observable<DocumentFile> {
        const copy = this.convert(documentFile);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(documentFile: DocumentFile): Observable<DocumentFile> {
        const copy = this.convert(documentFile);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<DocumentFile> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
        ;
    }
    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }

    private convert(documentFile: DocumentFile): DocumentFile {
        const copy: DocumentFile = Object.assign({}, documentFile);
        return copy;
    }
}
