import {Component} from '@angular/core';
import {AdminService} from "../../service/admin.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {OrderDTO} from "../../../interface/GlobalTypes";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent {

  orders: OrderDTO[];

  constructor(private adminService: AdminService, private snackBar: MatSnackBar) {
  }


  ngOnInit() {
    this.getPlacedOrders();
  }

  getPlacedOrders() {
    this.adminService.getPlacedOrders().subscribe(res => {
      this.orders = res;
    })
  }

  changeOrderStatus(orderId: number, status: string) {
    this.adminService.changeOrderStatus(orderId, status).subscribe(res => {
      if (res.id != null) {
        this.snackBar.open("Order status changed", "Close", {duration: 5000});
        this.getPlacedOrders();
      } else {
        this.snackBar.open("Something went wrong.", "Close", {duration: 5000});
      }
    })
  }

}
