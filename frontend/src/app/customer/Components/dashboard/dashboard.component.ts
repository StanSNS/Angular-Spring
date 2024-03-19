import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CustomerService} from "../../services/customer.service";
import {Product} from "../../../interface/GlobalTypes";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  products: Product[] = [];
  searchProductForm!: FormGroup;

  constructor(private customerService: CustomerService, private fb: FormBuilder, private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.getAllProducts();
    this.searchProductForm = this.fb.group({
      title: [null, [Validators.required]]
    })
  }

  getAllProducts() {
    this.products = [];
    this.customerService.getAllProducts().subscribe(res => {
      res.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,' + element.byteImage;
        this.products.push(element);
      })
    })
  }

  submitForm() {
    this.products = [];
    const title = this.searchProductForm.get('title')!.value;
    this.customerService.getAllProductsByName(title).subscribe(res => {
      res.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,' + element.byteImage;
        this.products.push(element);
      })
    })
  }

  addToCart(id: string) {
    this.customerService.addToCart(id).subscribe(res => {
      this.snackBar.open("Product added to cart", "Close", {duration: 5000})
    })
  }
}
