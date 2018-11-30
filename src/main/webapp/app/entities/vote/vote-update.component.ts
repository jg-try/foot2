import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVote } from 'app/shared/model/vote.model';
import { VoteService } from './vote.service';
import { IMatch } from 'app/shared/model/match.model';
import { MatchService } from 'app/entities/match';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player';

@Component({
    selector: 'jhi-vote-update',
    templateUrl: './vote-update.component.html'
})
export class VoteUpdateComponent implements OnInit {
    vote: IVote;
    isSaving: boolean;

    matches: IMatch[];

    players: IPlayer[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private voteService: VoteService,
        private matchService: MatchService,
        private playerService: PlayerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vote }) => {
            this.vote = vote;
        });
        this.matchService.query().subscribe(
            (res: HttpResponse<IMatch[]>) => {
                this.matches = res.body;
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
        if (this.vote.id !== undefined) {
            this.subscribeToSaveResponse(this.voteService.update(this.vote));
        } else {
            this.subscribeToSaveResponse(this.voteService.create(this.vote));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVote>>) {
        result.subscribe((res: HttpResponse<IVote>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMatchById(index: number, item: IMatch) {
        return item.id;
    }

    trackPlayerById(index: number, item: IPlayer) {
        return item.id;
    }
}
