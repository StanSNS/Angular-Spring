import {Component} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {CustomerService} from "../../../services/customer.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {elementAt, isEmpty} from "rxjs";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  cartItems: any[] = [];
  order: any;

  constructor(private customerService: CustomerService, private fb: FormBuilder, private snackBar: MatSnackBar,) {
  }

  ngOnInit():void{
    this.getCart();
  }


  getCart() {
    this.cartItems = [];
    this.customerService.getCartByUserId().subscribe(res => {
      this.order = res;
      res.cartItems.forEach(el => {
        el.processedImg = 'data:image/jpeg;base64,' + el.returnedImg;
        this.cartItems.push(el);
      })


    })
  }


}
