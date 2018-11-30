/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FootTestModule } from '../../../test.module';
import { StadiumUpdateComponent } from 'app/entities/stadium/stadium-update.component';
import { StadiumService } from 'app/entities/stadium/stadium.service';
import { Stadium } from 'app/shared/model/stadium.model';

describe('Component Tests', () => {
    describe('Stadium Management Update Component', () => {
        let comp: StadiumUpdateComponent;
        let fixture: ComponentFixture<StadiumUpdateComponent>;
        let service: StadiumService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FootTestModule],
                declarations: [StadiumUpdateComponent]
            })
                .overrideTemplate(StadiumUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StadiumUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StadiumService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Stadium(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.stadium = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Stadium();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.stadium = entity;
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
