import { Moment } from 'moment';
import { ITeamC } from 'app/shared/model//team-c.model';
import { ITeamS } from 'app/shared/model//team-s.model';
import { IStadium } from 'app/shared/model//stadium.model';
import { IPlayer } from 'app/shared/model//player.model';
import { IVote } from 'app/shared/model//vote.model';

export interface IMatch {
    id?: number;
    date?: Moment;
    scoreC?: number;
    scoreS?: number;
    teamC?: ITeamC;
    teamS?: ITeamS;
    stadium?: IStadium;
    mvpC?: IPlayer;
    mvpS?: IPlayer;
    votes?: IVote[];
}

export class Match implements IMatch {
    constructor(
        public id?: number,
        public date?: Moment,
        public scoreC?: number,
        public scoreS?: number,
        public teamC?: ITeamC,
        public teamS?: ITeamS,
        public stadium?: IStadium,
        public mvpC?: IPlayer,
        public mvpS?: IPlayer,
        public votes?: IVote[]
    ) {}
}
