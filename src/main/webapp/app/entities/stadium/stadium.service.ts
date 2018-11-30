import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStadium } from 'app/shared/model/stadium.model';

type EntityResponseType = HttpResponse<IStadium>;
type EntityArrayResponseType = HttpResponse<IStadium[]>;

@Injectable({ providedIn: 'root' })
export class StadiumService {
    public resourceUrl = SERVER_API_URL + 'api/stadiums';

    constructor(private http: HttpClient) {}

    create(stadium: IStadium): Observable<EntityResponseType> {
        return this.http.post<IStadium>(this.resourceUrl, stadium, { observe: 'response' });
    }

    update(stadium: IStadium): Observable<EntityResponseType> {
        return this.http.put<IStadium>(this.resourceUrl, stadium, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStadium>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStadium[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
