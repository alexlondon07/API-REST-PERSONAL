import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GLOBAL } from './global';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: "root",
})
export class ShowsService {
  public url: string;

  public clients: any[];

  constructor(private _http: HttpClient) {
    this.url = GLOBAL.urlTV;
  }

  /**
   * Método para obtener todos los programas de TV
   */
  getShows() {
    return this._http.get(this.url + "/search/shows?q=all");
  }

  /**
   * Método para obtener todos los programas de TV
   * @param id Identificador del show tv
   */
  getShowById(id: number): Observable<any> {
    return this._http.get(this.url + "/shows/" + id);
  }

  /**
   * Método para obtener el reparto de un programa de Tv
   */
  getShowCastById(id: number): Observable<any> {
    return this._http.get(this.url + "/shows/" + id + "/cast");
  }

  /**
   * Método para obtener las temporadas de un programa de Tv
   */
  getShowSeasonsById(id: number): Observable<any> {
    return this._http.get(this.url + "/shows/" + id + "/seasons");
  }

  /**
   * Método para obtener las temporadas de un programa de Tv por medio de palabra claves
   */
  getShowByKeywords(text: string): Observable<any> {
    console.log(this.url + "/singlesearch/shows/?q=" + text);
    http: return this._http.get(this.url + "/singlesearch/shows/?q=" + text);
  }
}