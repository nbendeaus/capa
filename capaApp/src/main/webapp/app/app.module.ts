import './vendor.ts';
import './polyfills';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';
import { Ng2BootstrapModule} from 'ngx-bootstrap';

import { CapaAppSharedModule, UserRouteAccessService } from './shared';
import { CapaAppHomeModule } from './home/home.module';
import { CapaAppAdminModule } from './admin/admin.module';
import { CapaAppAccountModule } from './account/account.module';
import { CapaAppEntityModule } from './entities/entity.module';

import { NAV_DROPDOWN_DIRECTIVES } from './shared/nav-dropdown.directive';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { SIDEBAR_TOGGLE_DIRECTIVES } from './shared/sidebar.directive';
import { AsideToggleDirective } from './shared/aside.directive';
import { BreadcrumbsComponent } from './shared/breadcrumb.component';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent,
    P500Component,
    P404Component,
    FullLayoutComponent,
    SimpleLayoutComponent,
} from './layouts';

import {DashboardModule} from "./dashboard/dashboard.module";


@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),

        CapaAppSharedModule,
        CapaAppHomeModule,
        CapaAppAdminModule,
        CapaAppAccountModule,
        CapaAppEntityModule,

        DashboardModule,

        ChartsModule,
        Ng2BootstrapModule.forRoot(),
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        P500Component,
        P404Component,
        NAV_DROPDOWN_DIRECTIVES,
        BreadcrumbsComponent,
        SIDEBAR_TOGGLE_DIRECTIVES,
        AsideToggleDirective,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
        FullLayoutComponent,
        SimpleLayoutComponent,
    ],
    entryComponents:[

    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService,

    ],
    bootstrap: [ JhiMainComponent ]
})
export class CapaAppAppModule {}
