import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from '@angular/router';
import { ShowsService } from '../../services/shows.service';

@Component({
  selector: "app-show-detail",
  templateUrl: "./show-detail.component.html",
})
export class ShowDetailComponent implements OnInit, OnDestroy {
  id: number;
  private sub: any;
  public show: any = {};

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
            }
          },
          (error) => {
            console.log(<any>error);
          }
        );
    });
  }

  ngOnDestroy() {

  }
}
