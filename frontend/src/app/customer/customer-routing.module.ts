import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerComponent } from './customer.component';
import {DashboardComponent} from "./Components/dashboard/dashboard.component";
import {CartComponent} from "./Components/cart/cart/cart.component";
import {authGuardGuard} from "../services/guard/auth-guard.guard";

const routes: Routes = [
  { path: '', component: CustomerComponent, canActivate: [authGuardGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuardGuard]},
  { path: 'cart', component: CartComponent , canActivate: [authGuardGuard]},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
