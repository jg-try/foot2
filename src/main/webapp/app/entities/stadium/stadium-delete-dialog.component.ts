import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStadium } from 'app/shared/model/stadium.model';
import { StadiumService } from './stadium.service';

@Component({
    selector: 'jhi-stadium-delete-dialog',
    templateUrl: './stadium-delete-dialog.component.html'
})
export class StadiumDeleteDialogComponent {
    stadium: IStadium;

    constructor(private stadiumService: StadiumService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stadiumService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'stadiumListModification',
                content: 'Deleted an stadium'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stadium-delete-popup',
    template: ''
})
export class StadiumDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stadium }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StadiumDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.stadium = stadium;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
