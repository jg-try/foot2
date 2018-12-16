import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { IPlayer } from 'app/shared/model/player.model';

@Injectable({
    providedIn: 'root'
})
export class MvpService {
    public resourceUrl = SERVER_API_URL;

    constructor(private http: HttpClient) {}

    getPlayers(matchId: number): Observable<HttpResponse<IPlayer[]>> {
        const url = this.resourceUrl + `/api/match/${matchId}/players`;
        return this.http.get<IPlayer[]>(url, { observe: 'response' });
    }
}
