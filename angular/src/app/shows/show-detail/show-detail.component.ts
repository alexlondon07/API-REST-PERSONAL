import { Component, OnInit, OnDestroy, Input, Output } from "@angular/core";
import { ActivatedRoute } from '@angular/router';
import { ShowsService } from '../../services/shows.service';

@Component({
  selector: "app-show-detail",
  templateUrl: "./show-detail.component.html",
})
export class ShowDetailComponent implements OnInit, OnDestroy {
  id: number;
  public show: any = {};
  public rating: number = 10;
  public starCount: number = 5;
  public ratingArr = [];

  constructor(
    private route: ActivatedRoute,
    private _showService: ShowsService
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.id = +params["id"];
      this._showService.getShowById(this.id).subscribe(
        (data) => {
          if (data) {
            this.show = data;
            for (let index = 0; index < this.show.rating.average; index++) {
              this.ratingArr.push(index);
            }
          }
        },
        (error) => {
          console.log(<any>error);
        }
      );
    });
  }

  ngOnDestroy() {}

  showIcon(index: number) {
    if (this.rating >= index + 1) {
      return "star";
    } else {
      return "star_border";
    }
  }
}
