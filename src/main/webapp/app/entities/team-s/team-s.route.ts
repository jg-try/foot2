import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TeamS } from 'app/shared/model/team-s.model';
import { TeamSService } from './team-s.service';
import { TeamSComponent } from './team-s.component';
import { TeamSDetailComponent } from './team-s-detail.component';
import { TeamSUpdateComponent } from './team-s-update.component';
import { TeamSDeletePopupComponent } from './team-s-delete-dialog.component';
import { ITeamS } from 'app/shared/model/team-s.model';

@Injectable({ providedIn: 'root' })
export class TeamSResolve implements Resolve<ITeamS> {
    constructor(private service: TeamSService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TeamS> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TeamS>) => response.ok),
                map((teamS: HttpResponse<TeamS>) => teamS.body)
            );
        }
        return of(new TeamS());
    }
}

export const teamSRoute: Routes = [
    {
        path: 'team-s',
        component: TeamSComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.teamS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'team-s/:id/view',
        component: TeamSDetailComponent,
        resolve: {
            teamS: TeamSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.teamS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'team-s/new',
        component: TeamSUpdateComponent,
        resolve: {
            teamS: TeamSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.teamS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'team-s/:id/edit',
        component: TeamSUpdateComponent,
        resolve: {
            teamS: TeamSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.teamS.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const teamSPopupRoute: Routes = [
    {
        path: 'team-s/:id/delete',
        component: TeamSDeletePopupComponent,
        resolve: {
            teamS: TeamSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.teamS.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
