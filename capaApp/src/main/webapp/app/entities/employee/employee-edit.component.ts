import {Component, OnDestroy, OnInit} from "@angular/core";
import {Employee} from "./employee.model";
import {ActivatedRoute, Router} from "@angular/router";
import {EmployeeService} from "./employee.service";
import {AlertService, EventManager} from "ng-jhipster";
import {Observable} from "rxjs/Observable";

@Component({
    selector: 'employee-edit',
    templateUrl: './employee-edit.component.html'
})
export class EmployeeEditComponent implements OnInit {

    employee: Employee;
    authorities: any[];
    isSaving: boolean;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private router : Router,
        private eventManager: EventManager,
        private alertService: AlertService,
        private employeeService : EmployeeService,
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
            this.employeeService.find(id).subscribe((employee) => {
                this.employee = employee;
            });
        } else {
            this.employee = new Employee();
        }
    }

    save() {
        this.isSaving = true;
        if (this.employee.id !== undefined) {
            console.log("Update: " + this.employee.firstName);
            this.subscribeToSaveResponse(
                this.employeeService.update(this.employee));
        } else {
            console.log("Create: " + this.employee.firstName);
            this.subscribeToSaveResponse(
                this.employeeService.create(this.employee));
        }
    }


    back() {
       this.router.navigate(['/dashboard/employee']);
    }

    private subscribeToSaveResponse(result: Observable<Employee>) {
        result.subscribe((res: Employee) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Employee) {
        this.eventManager.broadcast({ name: 'employeeListModification', content: 'OK'});
        this.isSaving = false;
        this.alertService.success("Employee successfully updated or created", null, null);
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
