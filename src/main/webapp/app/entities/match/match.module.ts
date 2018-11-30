import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FootSharedModule } from 'app/shared';
import {
    MatchComponent,
    MatchDetailComponent,
    MatchUpdateComponent,
    MatchDeletePopupComponent,
    MatchDeleteDialogComponent,
    matchRoute,
    matchPopupRoute
} from './';

const ENTITY_STATES = [...matchRoute, ...matchPopupRoute];

@NgModule({
    imports: [FootSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [MatchComponent, MatchDetailComponent, MatchUpdateComponent, MatchDeleteDialogComponent, MatchDeletePopupComponent],
    entryComponents: [MatchComponent, MatchUpdateComponent, MatchDeleteDialogComponent, MatchDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FootMatchModule {}
