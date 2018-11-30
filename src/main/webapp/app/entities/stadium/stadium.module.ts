import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FootSharedModule } from 'app/shared';
import {
    StadiumComponent,
    StadiumDetailComponent,
    StadiumUpdateComponent,
    StadiumDeletePopupComponent,
    StadiumDeleteDialogComponent,
    stadiumRoute,
    stadiumPopupRoute
} from './';

const ENTITY_STATES = [...stadiumRoute, ...stadiumPopupRoute];

@NgModule({
    imports: [FootSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StadiumComponent,
        StadiumDetailComponent,
        StadiumUpdateComponent,
        StadiumDeleteDialogComponent,
        StadiumDeletePopupComponent
    ],
    entryComponents: [StadiumComponent, StadiumUpdateComponent, StadiumDeleteDialogComponent, StadiumDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FootStadiumModule {}
