import { IPlayer } from 'app/shared/model//player.model';
import { IMatch } from 'app/shared/model//match.model';

export interface ITeamC {
    id?: number;
    name?: string;
    playerCS?: IPlayer[];
    match?: IMatch;
}

export class TeamC implements ITeamC {
    constructor(public id?: number, public name?: string, public playerCS?: IPlayer[], public match?: IMatch) {}
}
