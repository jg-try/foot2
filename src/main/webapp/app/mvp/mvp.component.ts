import { Component, OnInit } from '@angular/core';
import { IMatch } from '../shared/model/match.model';
import { IPlayer } from '../shared/model/player.model';
import { MatchService } from '../entities/match/match.service';
import { MvpService } from '../mvp/mvp.service';
import { HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ITEMS_PER_PAGE } from 'app/shared';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';

@Component({
    selector: 'jhi-mvp',
    templateUrl: './mvp.component.html',
    styles: []
})
export class MvpComponent implements OnInit {
    matches: IMatch[];
    players: IPlayer[];
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;

    allRatings: Map<number, number>;

    constructor(
        private matchService: MatchService,
        private mvpService: MvpService,
        private jhiAlertService: JhiAlertService,
        private parseLinks: JhiParseLinks
    ) {
        this.matches = [];
        this.players = [];
        this.allRatings = new Map<number, number>();
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    ngOnInit() {
        this.loadAll();
    }

    ratingComponentClick(clickObj: any): void {
        const player = this.players.find((i: any) => i.id === clickObj.playerId);
        if (!!player) {
            if (this.allRatings.has(player.id)) {
                this.allRatings.delete(player.id);
            }
            this.allRatings.set(player.id, clickObj.rating);
        }
    }

    validateRating() {
        console.log(this.allRatings);
    }

    cleanPlayers() {
        this.players = [];
    }

    cleanRatings() {
        this.allRatings.clear();
    }

    getPlayers(matchId: number) {
        this.cleanPlayers();
        this.cleanRatings();
        this.mvpService.getPlayers(matchId).subscribe(
            (res: HttpResponse<IPlayer[]>) => {
                for (let i = 0; i < res.body.length; i++) {
                    this.players.push(res.body[i]);
                }
            },
            (res: HttpErrorResponse) => {
                this.onError(res.message);
            }
        );
    }

    loadAll() {
        this.matchService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IMatch[]>) => this.paginateMatches(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateMatches(data: IMatch[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.matches.push(data[i]);
        }
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
