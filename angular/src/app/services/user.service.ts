import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GLOBAL } from './global';
import { Observable } from 'rxjs';
import { User } from '../models/User';

@Injectable({
  providedIn: "root",
})
export class UserService {
  public url: string;
  public users: any[];

  constructor(private _http: HttpClient) {
    this.url = GLOBAL.urlBackendSpringBoot;
  }

  /**
   * Método para obetenr usuarios registrados en la bd
   */
  getUsers(): Observable<any> {
    return this._http.get(this.url + "/users");
  }

  /**
   * Método para crear un nuevo usuario
   * @param user
   */
  createUser(user: User) {
    if (user.id > 0) {
      // Edit
      return this._http.post(this.url + "/users/" + user.id, user);
    } else {
      // Post
      return this._http.post(this.url + "/users", user);
    }
  }

  /**
   * Método para eliminar un usuario
   * @param id
   */
  deleteUser(id: number) {
    return this._http.delete(this.url + "/user/" + id);
  }

  /**
   * Método para obtener un usuario
   * @param id
   */
  getDataById(id: number) {
    http: return this._http.get(this.url + "/users?id=" + id);
  }
}
