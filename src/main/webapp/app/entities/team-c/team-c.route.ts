import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TeamC } from 'app/shared/model/team-c.model';
import { TeamCService } from './team-c.service';
import { TeamCComponent } from './team-c.component';
import { TeamCDetailComponent } from './team-c-detail.component';
import { TeamCUpdateComponent } from './team-c-update.component';
import { TeamCDeletePopupComponent } from './team-c-delete-dialog.component';
import { ITeamC } from 'app/shared/model/team-c.model';

@Injectable({ providedIn: 'root' })
export class TeamCResolve implements Resolve<ITeamC> {
    constructor(private service: TeamCService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TeamC> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TeamC>) => response.ok),
                map((teamC: HttpResponse<TeamC>) => teamC.body)
            );
        }
        return of(new TeamC());
    }
}

export const teamCRoute: Routes = [
    {
        path: 'team-c',
        component: TeamCComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.teamC.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'team-c/:id/view',
        component: TeamCDetailComponent,
        resolve: {
            teamC: TeamCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.teamC.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'team-c/new',
        component: TeamCUpdateComponent,
        resolve: {
            teamC: TeamCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.teamC.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'team-c/:id/edit',
        component: TeamCUpdateComponent,
        resolve: {
            teamC: TeamCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.teamC.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const teamCPopupRoute: Routes = [
    {
        path: 'team-c/:id/delete',
        component: TeamCDeletePopupComponent,
        resolve: {
            teamC: TeamCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.teamC.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
