import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITeamC } from 'app/shared/model/team-c.model';
import { TeamCService } from './team-c.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player';
import { IMatch } from 'app/shared/model/match.model';
import { MatchService } from 'app/entities/match';

@Component({
    selector: 'jhi-team-c-update',
    templateUrl: './team-c-update.component.html'
})
export class TeamCUpdateComponent implements OnInit {
    teamC: ITeamC;
    isSaving: boolean;

    players: IPlayer[];

    matches: IMatch[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private teamCService: TeamCService,
        private playerService: PlayerService,
        private matchService: MatchService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ teamC }) => {
            this.teamC = teamC;
        });
        this.playerService.query().subscribe(
            (res: HttpResponse<IPlayer[]>) => {
                this.players = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.matchService.query().subscribe(
            (res: HttpResponse<IMatch[]>) => {
                this.matches = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.teamC.id !== undefined) {
            this.subscribeToSaveResponse(this.teamCService.update(this.teamC));
        } else {
            this.subscribeToSaveResponse(this.teamCService.create(this.teamC));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITeamC>>) {
        result.subscribe((res: HttpResponse<ITeamC>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPlayerById(index: number, item: IPlayer) {
        return item.id;
    }

    trackMatchById(index: number, item: IMatch) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
