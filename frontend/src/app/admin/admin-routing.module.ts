import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import {DashboardComponent} from "./Components/dashboard/dashboard.component";
import {PostCategoryComponent} from "./Components/category/post-category/post-category.component";
import {PostProductComponent} from "./Components/product/post-product/post-product.component";
import {PostCouponComponent} from "./Components/post-coupon/post-coupon.component";
import {CouponsComponent} from "./Components/coupons/coupons.component";
import {OrdersComponent} from "./Components/orders/orders.component";

const routes: Routes = [
  { path: '', component: AdminComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'category', component: PostCategoryComponent },
  { path: 'product', component: PostProductComponent },
  { path: 'post-coupon', component: PostCouponComponent },
  { path: 'coupons', component: CouponsComponent },
  { path: 'orders', component: OrdersComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
