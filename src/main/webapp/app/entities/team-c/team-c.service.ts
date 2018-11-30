import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITeamC } from 'app/shared/model/team-c.model';

type EntityResponseType = HttpResponse<ITeamC>;
type EntityArrayResponseType = HttpResponse<ITeamC[]>;

@Injectable({ providedIn: 'root' })
export class TeamCService {
    public resourceUrl = SERVER_API_URL + 'api/team-cs';

    constructor(private http: HttpClient) {}

    create(teamC: ITeamC): Observable<EntityResponseType> {
        return this.http.post<ITeamC>(this.resourceUrl, teamC, { observe: 'response' });
    }

    update(teamC: ITeamC): Observable<EntityResponseType> {
        return this.http.put<ITeamC>(this.resourceUrl, teamC, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITeamC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITeamC[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
