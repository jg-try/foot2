import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITeamS } from 'app/shared/model/team-s.model';

type EntityResponseType = HttpResponse<ITeamS>;
type EntityArrayResponseType = HttpResponse<ITeamS[]>;

@Injectable({ providedIn: 'root' })
export class TeamSService {
    public resourceUrl = SERVER_API_URL + 'api/team-s';

    constructor(private http: HttpClient) {}

    create(teamS: ITeamS): Observable<EntityResponseType> {
        return this.http.post<ITeamS>(this.resourceUrl, teamS, { observe: 'response' });
    }

    update(teamS: ITeamS): Observable<EntityResponseType> {
        return this.http.put<ITeamS>(this.resourceUrl, teamS, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITeamS>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITeamS[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
