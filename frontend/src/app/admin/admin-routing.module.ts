import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import {DashboardComponent} from "./Components/dashboard/dashboard.component";
import {PostCategoryComponent} from "./Components/category/post-category/post-category.component";
import {PostProductComponent} from "./Components/product/post-product/post-product.component";
import {PostCouponComponent} from "./Components/post-coupon/post-coupon.component";
import {CouponsComponent} from "./Components/coupons/coupons.component";
import {OrdersComponent} from "./Components/orders/orders.component";
import {authGuardGuard} from "../services/guard/auth-guard.guard";

const routes: Routes = [
  { path: '', component: AdminComponent , canActivate: [authGuardGuard]},
  { path: 'dashboard', component: DashboardComponent , canActivate: [authGuardGuard]},
  { path: 'category', component: PostCategoryComponent , canActivate: [authGuardGuard]},
  { path: 'product', component: PostProductComponent , canActivate: [authGuardGuard]},
  { path: 'post-coupon', component: PostCouponComponent, canActivate: [authGuardGuard] },
  { path: 'coupons', component: CouponsComponent, canActivate: [authGuardGuard] },
  { path: 'orders', component: OrdersComponent, canActivate: [authGuardGuard] },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
