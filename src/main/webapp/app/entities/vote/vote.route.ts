import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Vote } from 'app/shared/model/vote.model';
import { VoteService } from './vote.service';
import { VoteComponent } from './vote.component';
import { VoteDetailComponent } from './vote-detail.component';
import { VoteUpdateComponent } from './vote-update.component';
import { VoteDeletePopupComponent } from './vote-delete-dialog.component';
import { IVote } from 'app/shared/model/vote.model';

@Injectable({ providedIn: 'root' })
export class VoteResolve implements Resolve<IVote> {
    constructor(private service: VoteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Vote> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Vote>) => response.ok),
                map((vote: HttpResponse<Vote>) => vote.body)
            );
        }
        return of(new Vote());
    }
}

export const voteRoute: Routes = [
    {
        path: 'vote',
        component: VoteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.vote.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vote/:id/view',
        component: VoteDetailComponent,
        resolve: {
            vote: VoteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.vote.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vote/new',
        component: VoteUpdateComponent,
        resolve: {
            vote: VoteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.vote.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vote/:id/edit',
        component: VoteUpdateComponent,
        resolve: {
            vote: VoteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.vote.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const votePopupRoute: Routes = [
    {
        path: 'vote/:id/delete',
        component: VoteDeletePopupComponent,
        resolve: {
            vote: VoteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'footApp.vote.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
