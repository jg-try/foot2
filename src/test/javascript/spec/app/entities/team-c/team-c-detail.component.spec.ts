/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FootTestModule } from '../../../test.module';
import { TeamCDetailComponent } from 'app/entities/team-c/team-c-detail.component';
import { TeamC } from 'app/shared/model/team-c.model';

describe('Component Tests', () => {
    describe('TeamC Management Detail Component', () => {
        let comp: TeamCDetailComponent;
        let fixture: ComponentFixture<TeamCDetailComponent>;
        const route = ({ data: of({ teamC: new TeamC(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FootTestModule],
                declarations: [TeamCDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TeamCDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TeamCDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.teamC).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
