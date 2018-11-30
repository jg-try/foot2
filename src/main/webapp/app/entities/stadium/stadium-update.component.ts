import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IStadium } from 'app/shared/model/stadium.model';
import { StadiumService } from './stadium.service';

@Component({
    selector: 'jhi-stadium-update',
    templateUrl: './stadium-update.component.html'
})
export class StadiumUpdateComponent implements OnInit {
    stadium: IStadium;
    isSaving: boolean;

    constructor(private stadiumService: StadiumService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stadium }) => {
            this.stadium = stadium;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stadium.id !== undefined) {
            this.subscribeToSaveResponse(this.stadiumService.update(this.stadium));
        } else {
            this.subscribeToSaveResponse(this.stadiumService.create(this.stadium));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStadium>>) {
        result.subscribe((res: HttpResponse<IStadium>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
