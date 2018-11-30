import { IVote } from 'app/shared/model//vote.model';
import { ITeamS } from 'app/shared/model//team-s.model';
import { ITeamC } from 'app/shared/model//team-c.model';

export interface IPlayer {
    id?: number;
    name?: string;
    avatarContentType?: string;
    avatar?: any;
    win?: number;
    lose?: number;
    draw?: number;
    mvp?: number;
    votes?: IVote[];
    teamS?: ITeamS[];
    teamCS?: ITeamC[];
}

export class Player implements IPlayer {
    constructor(
        public id?: number,
        public name?: string,
        public avatarContentType?: string,
        public avatar?: any,
        public win?: number,
        public lose?: number,
        public draw?: number,
        public mvp?: number,
        public votes?: IVote[],
        public teamS?: ITeamS[],
        public teamCS?: ITeamC[]
    ) {}
}
