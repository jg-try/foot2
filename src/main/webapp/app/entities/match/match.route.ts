import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Match } from 'app/shared/model/match.model';
import { MatchService } from './match.service';
import { MatchComponent } from './match.component';
import { MatchDetailComponent } from './match-detail.component';
import { MatchUpdateComponent } from './match-update.component';
import { MatchDeletePopupComponent } from './match-delete-dialog.component';
import { IMatch } from 'app/shared/model/match.model';

@Injectable({ providedIn: 'root' })
export class MatchResolve implements Resolve<IMatch> {
    constructor(private service: MatchService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Match> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Match>) => response.ok),
                map((match: HttpResponse<Match>) => match.body)
            );
        }
        return of(new Match());
    }
}

export const matchRoute: Routes = [
    {
        path: 'match',
        component: MatchComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.match.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'match/:id/view',
        component: MatchDetailComponent,
        resolve: {
            match: MatchResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.match.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'match/new',
        component: MatchUpdateComponent,
        resolve: {
            match: MatchResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.match.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'match/:id/edit',
        component: MatchUpdateComponent,
        resolve: {
            match: MatchResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.match.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const matchPopupRoute: Routes = [
    {
        path: 'match/:id/delete',
        component: MatchDeletePopupComponent,
        resolve: {
            match: MatchResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.match.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
