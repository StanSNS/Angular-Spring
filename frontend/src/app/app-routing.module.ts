import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {authGuardGuard} from "./services/guard/auth-guard.guard";
import {NotFoundComponent} from "./ErrorPage/not-found/not-found.component";

const routes: Routes = [
  {path: "login", component: LoginComponent, canActivate: [authGuardGuard]},
  {path: "register", component: RegisterComponent , canActivate: [authGuardGuard]},
  {path: 'customer', loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule) },
  {path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)},
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
