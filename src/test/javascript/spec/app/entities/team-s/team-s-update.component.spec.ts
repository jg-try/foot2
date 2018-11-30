/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FootTestModule } from '../../../test.module';
import { TeamSUpdateComponent } from 'app/entities/team-s/team-s-update.component';
import { TeamSService } from 'app/entities/team-s/team-s.service';
import { TeamS } from 'app/shared/model/team-s.model';

describe('Component Tests', () => {
    describe('TeamS Management Update Component', () => {
        let comp: TeamSUpdateComponent;
        let fixture: ComponentFixture<TeamSUpdateComponent>;
        let service: TeamSService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FootTestModule],
                declarations: [TeamSUpdateComponent]
            })
                .overrideTemplate(TeamSUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TeamSUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeamSService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TeamS(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.teamS = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TeamS();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.teamS = entity;
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
