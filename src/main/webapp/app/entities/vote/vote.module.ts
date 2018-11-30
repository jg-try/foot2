import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FootSharedModule } from 'app/shared';
import {
    VoteComponent,
    VoteDetailComponent,
    VoteUpdateComponent,
    VoteDeletePopupComponent,
    VoteDeleteDialogComponent,
    voteRoute,
    votePopupRoute
} from './';

const ENTITY_STATES = [...voteRoute, ...votePopupRoute];

@NgModule({
    imports: [FootSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [VoteComponent, VoteDetailComponent, VoteUpdateComponent, VoteDeleteDialogComponent, VoteDeletePopupComponent],
    entryComponents: [VoteComponent, VoteUpdateComponent, VoteDeleteDialogComponent, VoteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FootVoteModule {}
