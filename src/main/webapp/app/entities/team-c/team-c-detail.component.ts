import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeamC } from 'app/shared/model/team-c.model';

@Component({
    selector: 'jhi-team-c-detail',
    templateUrl: './team-c-detail.component.html'
})
export class TeamCDetailComponent implements OnInit {
    teamC: ITeamC;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ teamC }) => {
            this.teamC = teamC;
        });
    }

    previousState() {
        window.history.back();
    }
}
