/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FootTestModule } from '../../../test.module';
import { TeamSDeleteDialogComponent } from 'app/entities/team-s/team-s-delete-dialog.component';
import { TeamSService } from 'app/entities/team-s/team-s.service';

describe('Component Tests', () => {
    describe('TeamS Management Delete Component', () => {
        let comp: TeamSDeleteDialogComponent;
        let fixture: ComponentFixture<TeamSDeleteDialogComponent>;
        let service: TeamSService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FootTestModule],
                declarations: [TeamSDeleteDialogComponent]
            })
                .overrideTemplate(TeamSDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TeamSDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeamSService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
