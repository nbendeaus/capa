import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiGatewayComponent } from './gateway.component';

export const gatewayRoute: Route = {
    path: 'gateway',
    component: JhiGatewayComponent,
    data: {
        pageTitle: 'gateway.title',
        title: 'Gateway',
    }
};
