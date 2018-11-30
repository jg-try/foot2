import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeamS } from 'app/shared/model/team-s.model';
import { TeamSService } from './team-s.service';

@Component({
    selector: 'jhi-team-s-delete-dialog',
    templateUrl: './team-s-delete-dialog.component.html'
})
export class TeamSDeleteDialogComponent {
    teamS: ITeamS;

    constructor(private teamSService: TeamSService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.teamSService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'teamSListModification',
                content: 'Deleted an teamS'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-team-s-delete-popup',
    template: ''
})
export class TeamSDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ teamS }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TeamSDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.teamS = teamS;
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
