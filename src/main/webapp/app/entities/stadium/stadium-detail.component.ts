import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStadium } from 'app/shared/model/stadium.model';

@Component({
    selector: 'jhi-stadium-detail',
    templateUrl: './stadium-detail.component.html'
})
export class StadiumDetailComponent implements OnInit {
    stadium: IStadium;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stadium }) => {
            this.stadium = stadium;
        });
    }

    previousState() {
        window.history.back();
    }
}
