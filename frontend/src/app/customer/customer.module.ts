import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CustomerRoutingModule} from './customer-routing.module';
import {CustomerComponent} from './customer.component';
import {DashboardComponent} from './Components/dashboard/dashboard.component';
import {MatInputModule} from "@angular/material/input";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {HttpClientModule} from "@angular/common/http";
import {CartComponent} from './Components/cart/cart/cart.component';
import {PlaceOrderComponent} from './Components/place-order/place-order.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatTableModule} from "@angular/material/table";


@NgModule({
  declarations: [
    CustomerComponent,
    DashboardComponent,
    CartComponent,
    PlaceOrderComponent,
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    MatInputModule,
    MatListModule,
    MatIconModule,
    MatCardModule,
    ReactiveFormsModule,
    MatButtonModule,
    FormsModule,
    HttpClientModule,
    MatDialogModule,
    MatTableModule,
  ]
})
export class CustomerModule {
}
