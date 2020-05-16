import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GLOBAL } from './global';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Client } from 'app/models/Client';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  public url: string;

  public clients : any[];
  
  constructor(private _http: HttpClient){
      this.url = GLOBAL.url;
  }

  /**
   * Método para obtener los clientes de la Api de Firebase
   */
  getClients() {
    return this._http.get(this.url)
    .pipe(
        map( this.createArray )
    );
  }

  
  /**
   * Método para crear un nuevo cliente
   * @param client 
   */
  createClient( client: Client){
    return this._http.post(this.url, client);
  }

  /**
   * Metodo para convertir el objecto que retorna la Api de Firebase
   * @param clientsObj 
   */
  private createArray( clientsObj: object){
    
    const clients: Client[] = [];

    if( clientsObj === null ){ return []; }

    Object.keys( clientsObj ).forEach( key =>{
      const client: Client = clientsObj[key];
      clients.push(client);
    });

    return clients;
  }
}
