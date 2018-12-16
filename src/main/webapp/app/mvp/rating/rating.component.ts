import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-rating',
    templateUrl: './rating.component.html',
    styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {
    // FROM http://embed.plnkr.co/Qi6tEdIyaRoGgSj2z4M9/

    @Input()
    rating: number;
    @Input()
    playerId: number;
    @Output()
    ratingClick: EventEmitter<any> = new EventEmitter<any>();
    inputName: string;

    ngOnInit() {
        this.inputName = this.playerId + '_rating';
    }

    onClick(vote: number): void {
        this.rating = vote;
        this.ratingClick.emit({
            playerId: this.playerId,
            rating: vote
        });
    }
}
