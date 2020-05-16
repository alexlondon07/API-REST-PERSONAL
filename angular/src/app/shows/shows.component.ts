import { Component, OnInit } from '@angular/core';
import { ShowsService } from '../services/shows.service';

@Component({
  selector: "app-shows",
  templateUrl: "./shows.component.html",
  styleUrls: ["./shows.component.scss"],
})
export class ShowsComponent implements OnInit {
  public title: string;
  public description: string;
  public shows: any = [];

  constructor(private _showService: ShowsService) {
    this.title = "Shows TV";
    this.description = "Shows list from api tvmaze.com";
  }

  ngOnInit() {
    console.log("ShowsComponent loaded");
    this.getData();
  }

  /**
   * Método para obtener los de shows TV
   */
  getData() {
    this._showService.getShows().subscribe(
      (data) => {
        if (data) {
          this.shows = data;
          console.log("Component getData: ", this.shows);
        }
      },
      (error) => {
        console.log(<any>error);
      }
    );
  }
}