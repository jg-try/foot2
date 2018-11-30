/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FootTestModule } from '../../../test.module';
import { TeamCUpdateComponent } from 'app/entities/team-c/team-c-update.component';
import { TeamCService } from 'app/entities/team-c/team-c.service';
import { TeamC } from 'app/shared/model/team-c.model';

describe('Component Tests', () => {
    describe('TeamC Management Update Component', () => {
        let comp: TeamCUpdateComponent;
        let fixture: ComponentFixture<TeamCUpdateComponent>;
        let service: TeamCService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FootTestModule],
                declarations: [TeamCUpdateComponent]
            })
                .overrideTemplate(TeamCUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TeamCUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeamCService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TeamC(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.teamC = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TeamC();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.teamC = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
