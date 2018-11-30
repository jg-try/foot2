import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeamC } from 'app/shared/model/team-c.model';
import { TeamCService } from './team-c.service';

@Component({
    selector: 'jhi-team-c-delete-dialog',
    templateUrl: './team-c-delete-dialog.component.html'
})
export class TeamCDeleteDialogComponent {
    teamC: ITeamC;

    constructor(private teamCService: TeamCService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.teamCService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'teamCListModification',
                content: 'Deleted an teamC'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-team-c-delete-popup',
    template: ''
})
export class TeamCDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ teamC }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TeamCDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.teamC = teamC;
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
