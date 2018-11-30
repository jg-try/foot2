import { IPlayer } from 'app/shared/model//player.model';
import { IMatch } from 'app/shared/model//match.model';

export interface ITeamS {
    id?: number;
    name?: string;
    playerS?: IPlayer[];
    match?: IMatch;
}

export class TeamS implements ITeamS {
    constructor(public id?: number, public name?: string, public playerS?: IPlayer[], public match?: IMatch) {}
}
