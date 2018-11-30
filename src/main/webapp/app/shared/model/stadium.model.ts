import { IMatch } from 'app/shared/model//match.model';

export interface IStadium {
    id?: number;
    name?: string;
    matches?: IMatch[];
}

export class Stadium implements IStadium {
    constructor(public id?: number, public name?: string, public matches?: IMatch[]) {}
}
