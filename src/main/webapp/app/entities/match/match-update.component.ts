import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IMatch } from 'app/shared/model/match.model';
import { MatchService } from './match.service';
import { ITeamC } from 'app/shared/model/team-c.model';
import { TeamCService } from 'app/entities/team-c';
import { ITeamS } from 'app/shared/model/team-s.model';
import { TeamSService } from 'app/entities/team-s';
import { IStadium } from 'app/shared/model/stadium.model';
import { StadiumService } from 'app/entities/stadium';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player';

@Component({
    selector: 'jhi-match-update',
    templateUrl: './match-update.component.html'
})
export class MatchUpdateComponent implements OnInit {
    match: IMatch;
    isSaving: boolean;

    teamcs: ITeamC[];

    teams: ITeamS[];

    stadiums: IStadium[];

    players: IPlayer[];
    dateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private matchService: MatchService,
        private teamCService: TeamCService,
        private teamSService: TeamSService,
        private stadiumService: StadiumService,
        private playerService: PlayerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ match }) => {
            this.match = match;
        });
        this.teamCService.query({ filter: 'match-is-null' }).subscribe(
            (res: HttpResponse<ITeamC[]>) => {
                if (!this.match.teamC || !this.match.teamC.id) {
                    this.teamcs = res.body;
                } else {
                    this.teamCService.find(this.match.teamC.id).subscribe(
                        (subRes: HttpResponse<ITeamC>) => {
                            this.teamcs = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.teamSService.query({ filter: 'match-is-null' }).subscribe(
            (res: HttpResponse<ITeamS[]>) => {
                if (!this.match.teamS || !this.match.teamS.id) {
                    this.teams = res.body;
                } else {
                    this.teamSService.find(this.match.teamS.id).subscribe(
                        (subRes: HttpResponse<ITeamS>) => {
                            this.teams = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.stadiumService.query().subscribe(
            (res: HttpResponse<IStadium[]>) => {
                this.stadiums = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.playerService.query().subscribe(
            (res: HttpResponse<IPlayer[]>) => {
                this.players = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.match.id !== undefined) {
            this.subscribeToSaveResponse(this.matchService.update(this.match));
        } else {
            this.subscribeToSaveResponse(this.matchService.create(this.match));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMatch>>) {
        result.subscribe((res: HttpResponse<IMatch>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTeamCById(index: number, item: ITeamC) {
        return item.id;
    }

    trackTeamSById(index: number, item: ITeamS) {
        return item.id;
    }

    trackStadiumById(index: number, item: IStadium) {
        return item.id;
    }

    trackPlayerById(index: number, item: IPlayer) {
        return item.id;
    }
}
