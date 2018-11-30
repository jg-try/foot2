import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Stadium } from 'app/shared/model/stadium.model';
import { StadiumService } from './stadium.service';
import { StadiumComponent } from './stadium.component';
import { StadiumDetailComponent } from './stadium-detail.component';
import { StadiumUpdateComponent } from './stadium-update.component';
import { StadiumDeletePopupComponent } from './stadium-delete-dialog.component';
import { IStadium } from 'app/shared/model/stadium.model';

@Injectable({ providedIn: 'root' })
export class StadiumResolve implements Resolve<IStadium> {
    constructor(private service: StadiumService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Stadium> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Stadium>) => response.ok),
                map((stadium: HttpResponse<Stadium>) => stadium.body)
            );
        }
        return of(new Stadium());
    }
}

export const stadiumRoute: Routes = [
    {
        path: 'stadium',
        component: StadiumComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.stadium.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stadium/:id/view',
        component: StadiumDetailComponent,
        resolve: {
            stadium: StadiumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.stadium.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stadium/new',
        component: StadiumUpdateComponent,
        resolve: {
            stadium: StadiumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.stadium.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stadium/:id/edit',
        component: StadiumUpdateComponent,
        resolve: {
            stadium: StadiumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.stadium.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stadiumPopupRoute: Routes = [
    {
        path: 'stadium/:id/delete',
        component: StadiumDeletePopupComponent,
        resolve: {
            stadium: StadiumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.stadium.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
