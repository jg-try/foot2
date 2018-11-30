import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPlayer } from 'app/shared/model/player.model';

@Component({
    selector: 'jhi-player-detail',
    templateUrl: './player-detail.component.html'
})
export class PlayerDetailComponent implements OnInit {
    player: IPlayer;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ player }) => {
            this.player = player;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
