import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FootSharedModule } from 'app/shared';
import {
    TeamCComponent,
    TeamCDetailComponent,
    TeamCUpdateComponent,
    TeamCDeletePopupComponent,
    TeamCDeleteDialogComponent,
    teamCRoute,
    teamCPopupRoute
} from './';

const ENTITY_STATES = [...teamCRoute, ...teamCPopupRoute];

@NgModule({
    imports: [FootSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TeamCComponent, TeamCDetailComponent, TeamCUpdateComponent, TeamCDeleteDialogComponent, TeamCDeletePopupComponent],
    entryComponents: [TeamCComponent, TeamCUpdateComponent, TeamCDeleteDialogComponent, TeamCDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FootTeamCModule {}
