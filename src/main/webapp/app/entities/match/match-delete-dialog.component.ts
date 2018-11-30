import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMatch } from 'app/shared/model/match.model';
import { MatchService } from './match.service';

@Component({
    selector: 'jhi-match-delete-dialog',
    templateUrl: './match-delete-dialog.component.html'
})
export class MatchDeleteDialogComponent {
    match: IMatch;

    constructor(private matchService: MatchService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.matchService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'matchListModification',
                content: 'Deleted an match'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-match-delete-popup',
    template: ''
})
export class MatchDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ match }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MatchDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.match = match;
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
