import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomerService} from "../../../services/customer.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {elementAt, isEmpty} from "rxjs";
import {PlaceOrderComponent} from "../../place-order/place-order.component";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  cartItems: any[] = [];
  order: any;
  couponForm!: FormGroup;

  constructor(private customerService: CustomerService, private fb: FormBuilder, private snackBar: MatSnackBar, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.couponForm = this.fb.group({
      code: [null, Validators.required]
    })
    this.getCart();
  }

  applyCoupon() {
    this.customerService.applyCoupon(this.couponForm.get(['code'])!.value).subscribe(res => {
      this.snackBar.open("Coupon applied", "Close", {duration: 5000});
      this.getCart()
    }, error => {
      this.snackBar.open(error.error, 'Close', {duration: 5000})
    })
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

  increaseQuantity(productId: any) {
    this.customerService.increaseProductQuantity(productId).subscribe(res => {
      this.snackBar.open("Product quantity increased", "Close", {duration: 5000});
      this.getCart();
    })
  }

  decreaseQuantity(productId: any) {
    this.customerService.decreaseProductQuantity(productId).subscribe(res => {
      this.snackBar.open("Product quantity decreased", "Close", {duration: 5000});
      this.getCart();
    })
  }

  placeOrder() {
    this.dialog.open(PlaceOrderComponent);
  }

}
