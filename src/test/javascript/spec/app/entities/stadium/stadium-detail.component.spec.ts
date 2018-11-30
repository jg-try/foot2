/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FootTestModule } from '../../../test.module';
import { StadiumDetailComponent } from 'app/entities/stadium/stadium-detail.component';
import { Stadium } from 'app/shared/model/stadium.model';

describe('Component Tests', () => {
    describe('Stadium Management Detail Component', () => {
        let comp: StadiumDetailComponent;
        let fixture: ComponentFixture<StadiumDetailComponent>;
        const route = ({ data: of({ stadium: new Stadium(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FootTestModule],
                declarations: [StadiumDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StadiumDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StadiumDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stadium).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
