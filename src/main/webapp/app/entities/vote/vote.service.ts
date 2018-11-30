import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVote } from 'app/shared/model/vote.model';

type EntityResponseType = HttpResponse<IVote>;
type EntityArrayResponseType = HttpResponse<IVote[]>;

@Injectable({ providedIn: 'root' })
export class VoteService {
    public resourceUrl = SERVER_API_URL + 'api/votes';

    constructor(private http: HttpClient) {}

    create(vote: IVote): Observable<EntityResponseType> {
        return this.http.post<IVote>(this.resourceUrl, vote, { observe: 'response' });
    }

    update(vote: IVote): Observable<EntityResponseType> {
        return this.http.put<IVote>(this.resourceUrl, vote, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVote>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVote[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
