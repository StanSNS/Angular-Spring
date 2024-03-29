import {Component} from '@angular/core';
import {AdminService} from "../../service/admin.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ProductDTO} from "../../../interface/GlobalTypes";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  products: ProductDTO[] = [];
  searchProductForm!: FormGroup;

  constructor(private adminService: AdminService, private fb: FormBuilder, private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.getAllProducts();
    this.searchProductForm = this.fb.group({
      title: [null, [Validators.required]]
    })
  }

  getAllProducts() {
    this.products = [];
    this.adminService.getAllProducts().subscribe(res => {
      res.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,' + element.byteImage;
        this.products.push(element);
      })
    })
  }

  submitForm() {
    this.products = [];
    const title = this.searchProductForm.get('title')!.value;
    this.adminService.getAllProductsByName(title).subscribe(res => {
      res.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,' + element.byteImage;
        this.products.push(element);
      })
    })
  }

  deleteProduct(productId) {
    this.adminService.deleteProduct(productId).subscribe(res => {
      if (res == null) {
        this.snackBar.open('Product Deleted', 'Close', {duration: 5000});
        this.getAllProducts();
      } else {
        this.snackBar.open(res.message, 'Close', {duration: 5000, panelClass: 'error-snackbar'});
      }
    })
  }

}
