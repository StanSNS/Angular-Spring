import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerComponent } from './customer.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import {MatInputModule} from "@angular/material/input";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {HttpClientModule} from "@angular/common/http";


@NgModule({
  declarations: [
    CustomerComponent,
    DashboardComponent
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
  ]
})
export class CustomerModule { }
