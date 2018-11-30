import { IMatch } from 'app/shared/model//match.model';
import { IPlayer } from 'app/shared/model//player.model';

export interface IVote {
    id?: number;
    note?: string;
    match?: IMatch;
    voter?: IPlayer;
}

export class Vote implements IVote {
    constructor(public id?: number, public note?: string, public match?: IMatch, public voter?: IPlayer) {}
}
