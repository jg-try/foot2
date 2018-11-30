import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FootSharedModule } from 'app/shared';
import {
    TeamSComponent,
    TeamSDetailComponent,
    TeamSUpdateComponent,
    TeamSDeletePopupComponent,
    TeamSDeleteDialogComponent,
    teamSRoute,
    teamSPopupRoute
} from './';

const ENTITY_STATES = [...teamSRoute, ...teamSPopupRoute];

@NgModule({
    imports: [FootSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TeamSComponent, TeamSDetailComponent, TeamSUpdateComponent, TeamSDeleteDialogComponent, TeamSDeletePopupComponent],
    entryComponents: [TeamSComponent, TeamSUpdateComponent, TeamSDeleteDialogComponent, TeamSDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FootTeamSModule {}
