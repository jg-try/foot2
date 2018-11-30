/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FootTestModule } from '../../../test.module';
import { StadiumDeleteDialogComponent } from 'app/entities/stadium/stadium-delete-dialog.component';
import { StadiumService } from 'app/entities/stadium/stadium.service';

describe('Component Tests', () => {
    describe('Stadium Management Delete Component', () => {
        let comp: StadiumDeleteDialogComponent;
        let fixture: ComponentFixture<StadiumDeleteDialogComponent>;
        let service: StadiumService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FootTestModule],
                declarations: [StadiumDeleteDialogComponent]
            })
                .overrideTemplate(StadiumDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StadiumDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StadiumService);
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
