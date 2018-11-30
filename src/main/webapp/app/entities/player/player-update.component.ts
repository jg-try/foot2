import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from './player.service';
import { ITeamS } from 'app/shared/model/team-s.model';
import { TeamSService } from 'app/entities/team-s';
import { ITeamC } from 'app/shared/model/team-c.model';
import { TeamCService } from 'app/entities/team-c';

@Component({
    selector: 'jhi-player-update',
    templateUrl: './player-update.component.html'
})
export class PlayerUpdateComponent implements OnInit {
    player: IPlayer;
    isSaving: boolean;

    teams: ITeamS[];

    teamcs: ITeamC[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private playerService: PlayerService,
        private teamSService: TeamSService,
        private teamCService: TeamCService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ player }) => {
            this.player = player;
        });
        this.teamSService.query().subscribe(
            (res: HttpResponse<ITeamS[]>) => {
                this.teams = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.teamCService.query().subscribe(
            (res: HttpResponse<ITeamC[]>) => {
                this.teamcs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.player, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.player.id !== undefined) {
            this.subscribeToSaveResponse(this.playerService.update(this.player));
        } else {
            this.subscribeToSaveResponse(this.playerService.create(this.player));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPlayer>>) {
        result.subscribe((res: HttpResponse<IPlayer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTeamSById(index: number, item: ITeamS) {
        return item.id;
    }

    trackTeamCById(index: number, item: ITeamC) {
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
