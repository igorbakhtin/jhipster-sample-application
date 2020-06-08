import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOperator, Operator } from 'app/shared/model/operator.model';
import { OperatorService } from './operator.service';
import { OperatorComponent } from './operator.component';
import { OperatorDetailComponent } from './operator-detail.component';
import { OperatorUpdateComponent } from './operator-update.component';

@Injectable({ providedIn: 'root' })
export class OperatorResolve implements Resolve<IOperator> {
  constructor(private service: OperatorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOperator> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((operator: HttpResponse<Operator>) => {
          if (operator.body) {
            return of(operator.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Operator());
  }
}

export const operatorRoute: Routes = [
  {
    path: '',
    component: OperatorComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Operators',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OperatorDetailComponent,
    resolve: {
      operator: OperatorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Operators',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OperatorUpdateComponent,
    resolve: {
      operator: OperatorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Operators',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OperatorUpdateComponent,
    resolve: {
      operator: OperatorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Operators',
    },
    canActivate: [UserRouteAccessService],
  },
];
