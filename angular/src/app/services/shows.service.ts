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
    return this._http.get(this.url + "/shows/"+id);

    
    http://api.tvmaze.com/shows/1
}