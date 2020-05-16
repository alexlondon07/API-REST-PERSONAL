import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GLOBAL } from './global';

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
    return this._http.get(this.url + "/shows?q=all");
  }
}