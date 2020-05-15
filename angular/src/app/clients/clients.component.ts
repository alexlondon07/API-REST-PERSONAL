import { Component, OnInit } from "@angular/core";
import { Client } from "app/models/Client";
import { ClientService } from "app/services/client-service.service";

@Component({
  selector: "app-clients",
  templateUrl: "./clients.component.html"
})
export class ClientsComponent implements OnInit {
  
  public title: string;
  public description: string;
  public clients: any = [];

  constructor(private _clientService: ClientService) {
    this.title = "Clients";
    this.description = "Clients list from api Firebase.io";
  }

  ngOnInit() {
    console.log("ClientsComponent loaded");
    this.getData();
  }

  /**
   * Obtener los Clientes registrados
   */
  getData(){
    this._clientService.getClients().subscribe(
      data => {
        if (data) {
          this.clients = data;
          console.log('Component getClients: ', this.clients);
        }
      },
      error => {
        console.log(<any>error);
      }
    );
  }
}