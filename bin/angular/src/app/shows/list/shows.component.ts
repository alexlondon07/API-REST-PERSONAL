import { Component, OnInit } from '@angular/core';
import { ShowsService } from '../../services/shows.service';
import { element } from 'protractor';

@Component({
  selector: "app-shows",
  templateUrl: "./shows.component.html",
  styleUrls: ["./shows.component.scss"],
})
export class ShowsComponent implements OnInit {
  public title: string;
  public description: string;
  public shows: any = [];
  public showsInitial: any = [];
  public languages: any = [];
  public genres: any = [];
  public search: string;

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
          this.showsInitial = data;
          this.showsInitial.forEach((element) => {
            this.shows.push(element['show']);
          });
          this.showsInitial = this.shows;

          // Languages
          this.shows.forEach((element) => {
            this.languages.push(element.language);
          });
          var unique = this.languages.filter(function (elem, index, self) {
            return index === self.indexOf(elem);
          });
          this.languages = unique;

          // Genres
          this.shows.forEach((element) => {
            if (element.genres && element.genres.length > 0) {
              element.genres.forEach((row) => {
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

  /**
   * Método para filtrar las peliculas según tipo(idioma, genero, canal)
   */
  filter(type: string, option: string) {
    if (type != "" && option != "") {

      console.log(type);

      const filter: any = [];
      switch (type) {
        case "language":
          if (option == "allLanguages") {
            this.shows = this.showsInitial;
          } else {
            this.showsInitial.filter((element, i, arr) => {
              if (element.language.includes(option)) {
                filter.push(element);
              }
            });
            this.shows = filter;
          }
          break;

        case "keywords":
            this.getDataByKeywords(this.search);
          break;

        case "genres":
          if (option == "allGenres") {
            this.shows = this.showsInitial;
          } else {
            this.showsInitial.filter((element, i, arr) => {
              if (element.genres.includes(option)) {
                filter.push(element);
              }
            });
            this.shows = filter;
          }
          break;

        default:
          this.shows = this.showsInitial;
          break;
      }
    }
  }

  /**
   * Método para buscar peliculas por palabras claves
   */
  getDataByKeywords(text: string) {
    this._showService.getShowByKeywords(text).subscribe(
      (data) => {
        if (data) {
          this.shows = [];
          this.shows.push(data);
        }
      },
      (error) => {
        console.log(<any>error);
      }
    );
    }
}