/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FootTestModule } from '../../../test.module';
import { TeamSDetailComponent } from 'app/entities/team-s/team-s-detail.component';
import { TeamS } from 'app/shared/model/team-s.model';

describe('Component Tests', () => {
    describe('TeamS Management Detail Component', () => {
        let comp: TeamSDetailComponent;
        let fixture: ComponentFixture<TeamSDetailComponent>;
        const route = ({ data: of({ teamS: new TeamS(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FootTestModule],
                declarations: [TeamSDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TeamSDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TeamSDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.teamS).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
