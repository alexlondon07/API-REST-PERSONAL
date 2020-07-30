import { Routes } from '@angular/router';

//Clients Components 
import { ClientsComponent } from 'app/clients/clients.component';
import { ClientAddComponent } from 'app/client-add/client-add.component';
import { ShowsComponent } from '../../shows/list/shows.component';
import { ShowDetailComponent } from '../../shows/show-detail/show-detail.component';
import { UsersComponent } from '../../users/user-list/users.component';
import { CreateUserComponent } from 'app/users/create-user/create-user/create-user.component';

export const AdminLayoutRoutes: Routes = [
         // {
         //   path: '',
         //   children: [ {
         //     path: 'dashboard',
         //     component: DashboardComponent
         // }]}, {
         // path: '',
         // children: [ {
         //   path: 'userprofile',
         //   component: UserProfileComponent
         // }]
         // }, {
         //   path: '',
         //   children: [ {
         //     path: 'icons',
         //     component: IconsComponent
         //     }]
         // }, {
         //     path: '',
         //     children: [ {
         //         path: 'notifications',
         //         component: NotificationsComponent
         //     }]
         // }, {
         //     path: '',
         //     children: [ {
         //         path: 'maps',
         //         component: MapsComponent
         //     }]
         // }, {
         //     path: '',
         //     children: [ {
         //         path: 'typography',
         //         component: TypographyComponent
         //     }]
         // }, {
         //     path: '',
         //     children: [ {
         //         path: 'upgrade',
         //         component: UpgradeComponent
         //     }]
         // }
         { path: "shows", component: ShowsComponent },
         { path: "shows/:id", component: ShowDetailComponent },
         { path: "users", component: UsersComponent },
         { path: "user-add", component: CreateUserComponent },
         { path: "user-edit/:id", component: CreateUserComponent },
         { path: "client-add", component: ClientAddComponent },
         //  { path: "client", component: ClientsComponent },
         // { path: 'dashboard',      component: DashboardComponent },
         // { path: 'user-profile',   component: UserProfileComponent },
         // { path: 'table-list',     component: TableListComponent },
         // { path: 'typography',     component: TypographyComponent },
         // { path: 'icons',          component: IconsComponent },
         // { path: 'maps',           component: MapsComponent },
         // { path: 'notifications',  component: NotificationsComponent },
         // { path: 'upgrade',        component: UpgradeComponent },
       ];
