import { Component, OnInit } from "@angular/core";
import { UserService } from "../../services/user.service";
import { MatSnackBar } from "@angular/material";
import Swal from "sweetalert2";
import { User } from '../../models/User';
import { Router } from '@angular/router';

@Component({
  selector: "app-users",
  templateUrl: "./users.component.html",
  styleUrls: ["./users.component.scss"],
})
export class UsersComponent implements OnInit {
  public users: any = [];
  user: User = new User();

  constructor(
    private _userService: UserService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit() {
    this.getData();
  }

  /**
   * Método para obtener el listado de usuarios
   */
  getData() {
    this._userService.getUsers().subscribe(
      (data) => {
        if (data) {
          if (data["success"] && data["statusCode"] == 200) {
            this.users = data["data"];
            console.log(this.users);
          } else {
            this.showError("Error when getting the list of users");
          }
        }
      },
      (error) => {
        this.showError("Error when getting the list of users");
        console.log(<any>error);
      }
    );
  }

  /**
   * Método para obtener un usuario
   * @param id Identificador del usuario
   */
  goToEditUser(id: number) {
    this.router.navigate(["/user-edit/" + id]);
  }

  /**
   * Método para eliminar el listado de usuarios
   */
  deleteUser(id: number) {
    Swal.fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!",
    }).then((result) => {
      if (result.value) {
        this._userService.deleteUser(id).subscribe(
          (res) => {
            if (res && res["success"]) {
              Swal.fire({
                title: "Good job!",
                text: "Successful delete!",
                type: "success",
              });
              this.getData();
            } else {
              Swal.fire({
                title: "Error!!",
                text: res["message"],
                type: "error",
              });
            }
          },
          (error) => {
            this.showError("Error delete user");
            console.log(<any>error);
          }
        );
      }
    });
  }

  showError(msj: string) {
    this._snackBar.open(msj, "Accept", {
      duration: 2000,
    });
  }
}
