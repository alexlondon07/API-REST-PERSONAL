import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminLayoutRoutes } from './admin-layout.routing';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { UserProfileComponent } from '../../user-profile/user-profile.component';
import { TableListComponent } from '../../table-list/table-list.component';
import { TypographyComponent } from '../../typography/typography.component';
import { IconsComponent } from '../../icons/icons.component';
import { MapsComponent } from '../../maps/maps.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { UpgradeComponent } from '../../upgrade/upgrade.component';

import {
  MatButtonModule,
  MatInputModule,
  MatRippleModule,
  MatFormFieldModule,
  MatTooltipModule,
  MatSelectModule,
  MatIconModule,
  MatSnackBarModule,
} from "@angular/material";

import { ClientService } from 'app/services/client-service.service';
import { ClientAddComponent } from 'app/client-add/client-add.component';
import { ClientsComponent } from 'app/clients/clients.component';
import { ShowsComponent } from 'app/shows/list/shows.component';
import { ShowDetailComponent } from 'app/shows/show-detail/show-detail.component';
import { UsersComponent } from 'app/users/user-list/users.component';
import { UserService } from '../../services/user.service';
import { CreateUserComponent } from '../../users/create-user/create-user/create-user.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    MatButtonModule,
    MatRippleModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTooltipModule,
    MatIconModule,
    MatSnackBarModule
  ],
  declarations: [
    DashboardComponent,
    UserProfileComponent,
    TableListComponent,
    TypographyComponent,
    IconsComponent,
    MapsComponent,
    NotificationsComponent,
    UpgradeComponent,
    ClientsComponent,
    ClientAddComponent,
    ShowsComponent,
    ShowDetailComponent,
    UsersComponent,
    CreateUserComponent
  ],
  providers: [ClientService, UserService],
})
export class AdminLayoutModule {}
