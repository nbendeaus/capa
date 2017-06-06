import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Project } from './project.model';
import { DateUtils } from 'ng-jhipster';

@Injectable()
export class ProjectService {

    private resourceUrl = 'api/projects';
    private resourceSearchUrl = 'api/_search/projects';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(project: Project): Observable<Project> {
        const copy = this.convert(project);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(project: Project): Observable<Project> {
        const copy = this.convert(project);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Project> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.start = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.start);
            jsonResponse.end = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.end);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    private convertResponse(res: Response): Response {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].start = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].start);
            jsonResponse[i].end = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].end);
        }
        res.json().data = jsonResponse;
        return res;
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

    private convert(project: Project): Project {
        const copy: Project = Object.assign({}, project);
        copy.start = this.dateUtils
            .convertLocalDateToServer(project.start);
        copy.end = this.dateUtils
            .convertLocalDateToServer(project.end);
        return copy;
    }
}
