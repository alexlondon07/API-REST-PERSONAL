import { Component, OnInit } from '@angular/core';
import { ShowsService } from '../../services/shows.service';

@Component({
  selector: "app-shows",
  templateUrl: "./shows.component.html",
  styleUrls: ["./shows.component.scss"],
})
export class ShowsComponent implements OnInit {
  public title: string;
  public description: string;
  public shows: any = [];
  public languages: any = [];
  public genres: any = [];

  constructor(private _showService: ShowsService) {
    this.title = "Shows TV";
    this.description = "Shows list from api tvmaze.com";
  }

  ngOnInit() {
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

          // Languages
          this.shows.forEach((element) => {
            this.languages.push(element.show.language);
          });
          var unique = this.languages.filter(function (elem, index, self) {
            return index === self.indexOf(elem);
          });
          this.languages = unique;

          // Genres
          this.shows.forEach((element) => {
            if (element.show.genres && element.show.genres.length > 0) {
                element.show.genres.forEach((row) => {
                    this.genres.push(row);
                });
            }
          });
          var genresUnique = this.genres.filter(function (elem, index, self) {
            return index === self.indexOf(elem);
          });
          this.genres = genresUnique;
        }
      },
      (error) => {
        console.log(<any>error);
      }
    );
  }
}