import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Employee } from './employee.model';
import { DateUtils } from 'ng-jhipster';

@Injectable()
export class EmployeeService {

    private resourceUrl = 'api/employees';
    private resourceSearchUrl = 'api/_search/employees';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(employee: Employee): Observable<Employee> {
        const copy = this.convert(employee);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(employee: Employee): Observable<Employee> {
        const copy = this.convert(employee);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Employee> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.formDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.formDate);
            jsonResponse.toDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.toDate);
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
            jsonResponse[i].formDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].formDate);
            jsonResponse[i].toDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].toDate);
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

    private convert(employee: Employee): Employee {
        const copy: Employee = Object.assign({}, employee);
        copy.formDate = this.dateUtils
            .convertLocalDateToServer(employee.formDate);
        copy.toDate = this.dateUtils
            .convertLocalDateToServer(employee.toDate);
        return copy;
    }
}
