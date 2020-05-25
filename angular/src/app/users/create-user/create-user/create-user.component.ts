import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../../services/client-service.service';
import { NgForm } from '@angular/forms';
import { User } from '../../../models/User';
import Swal from "sweetalert2";
import { UserService } from '../../../services/user.service';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: "app-create-user",
  templateUrl: "./create-user.component.html",
  styleUrls: ["./create-user.component.scss"],
})
export class CreateUserComponent implements OnInit {
  public exist: string;
  user: User = new User();
  role: string;
  roles = [
    { value: "ADMIN", viewValue: "ADMIN" },
    { value: "USERS", viewValue: "USERS" },
  ];
  title: string = 'Create new user';
  constructor(
    private _route: ActivatedRoute,
    private _snackBar: MatSnackBar,
    private _userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    this._route.params.forEach((params: Params) => {
      let id = params["id"];
      if( id > 0){
        this.title = "Edit user " + id;
        this.edit(id);
      }
    });
  }

  saveUser(form: NgForm) {
    if (form.valid) {
      this._userService.createUser(this.user).subscribe((res) => {
        if (res && res["success"]) {
          Swal.fire({
            title: "Good job!",
            text: "Successful register!",
            type: "success",
          });
          form.reset();
          this.router.navigateByUrl("/users");
        } else {
          Swal.fire({
            title: "Error!!",
            text: res["message"],
            type: "error",
          });
        }
      });
    } else {
      Swal.fire({
        title: "Error!!",
        text: "You must validate the information to be entered!",
        type: "error",
      });
    }
  }

  /**
   * Método para obtener un usuario
   * @param id Identificador del usuario
   */
  edit(id: number) {
    this._userService.getDataById(id).subscribe(
      (data) => {
        if (data) {
          if (data["success"] && data["statusCode"] == 200) {
            this.user = data["data"];
          } else {
            this.showError("Error getting user");
          }
        }
      },
      (error) => {
        this.showError("Error getting user");
      }
    );
  }

  showError(msj: string) {
    this._snackBar.open(msj, "Accept", {
      duration: 2000,
    });
  }
}
