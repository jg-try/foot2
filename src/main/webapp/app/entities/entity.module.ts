import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FootPlayerModule } from './player/player.module';
import { FootMatchModule } from './match/match.module';
import { FootStadiumModule } from './stadium/stadium.module';
import { FootTeamCModule } from './team-c/team-c.module';
import { FootTeamSModule } from './team-s/team-s.module';
import { FootVoteModule } from './vote/vote.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        FootPlayerModule,
        FootMatchModule,
        FootStadiumModule,
        FootTeamCModule,
        FootTeamSModule,
        FootVoteModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FootEntityModule {}
