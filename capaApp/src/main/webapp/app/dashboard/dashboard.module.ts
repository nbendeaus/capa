import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { DashboardComponent } from './dashboard.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { CapaAppSharedModule } from "../shared/shared.module";

import 'chart.js'

@NgModule({
  imports: [
      CapaAppSharedModule,
      DashboardRoutingModule,
      ChartsModule,
      BsDropdownModule,
  ],
  declarations: [ DashboardComponent ],
  exports: [DashboardComponent],
  entryComponents: [
      DashboardComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardModule { }
