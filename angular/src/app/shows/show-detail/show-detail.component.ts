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
  public casts: any = [];
  public seasons: any = [];

  constructor(
    private route: ActivatedRoute,
    private _showService: ShowsService
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.id = +params["id"];
      this.getDataDetail();
    });
  }

  ngOnDestroy() {}

  /**
   * Metodo para obtener el detalle del programa de tv
   */
  getDataDetail() {
    this._showService.getShowById(this.id).subscribe(
      (data) => {
        if (data) {
          this.show = data;
          this.getDataCast();
        }
      },
      (error) => {
        console.log(<any>error);
      }
    );
  }

  /**
   * Metodo para obtener el el reparto del programa de tv
   */
  getDataCast() {
    this._showService.getShowCastById(this.id).subscribe(
      (data) => {
        if (data) {
          this.casts = data;
          this.getDataSeason();
        }
      },
      (error) => {
        console.log(<any>error);
      }
    );
  }

  /**
   * Metodo para obtener las temporadas del programa de tv
   */
  getDataSeason() {
    this._showService.getShowSeasonsById(this.id).subscribe(
      (data) => {
        if (data) {
          this.seasons = data;
        }
      },
      (error) => {
        console.log(<any>error);
      }
    );
  }

}
