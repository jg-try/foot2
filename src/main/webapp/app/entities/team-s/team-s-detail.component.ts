import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeamS } from 'app/shared/model/team-s.model';

@Component({
    selector: 'jhi-team-s-detail',
    templateUrl: './team-s-detail.component.html'
})
export class TeamSDetailComponent implements OnInit {
    teamS: ITeamS;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ teamS }) => {
            this.teamS = teamS;
        });
    }

    previousState() {
        window.history.back();
    }
}
