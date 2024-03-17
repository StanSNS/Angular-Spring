import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AdminService} from "../../service/admin.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-post-coupon',
  templateUrl: './post-coupon.component.html',
  styleUrls: ['./post-coupon.component.css']
})
export class PostCouponComponent {

  couponForm!: FormGroup;

  constructor(
    private adminService: AdminService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router) {
  }

  ngOnInit() {
    this.couponForm = this.fb.group({
      name: [null, [Validators.required]],
      code: [null, [Validators.required]],
      discount: [null, [Validators.required]],
      expirationDate: [null, [Validators.required]],
    })
  }

  addCoupon() {
    if (this.couponForm.valid) {
      this.adminService.addCoupon(this.couponForm.value).subscribe(res => {
        if (res.id != null) {
          this.snackBar.open("Coupon posted!", 'Close', {duration: 5000})
          this.router.navigateByUrl('/admin/dashboard');
        } else {
          this.snackBar.open(res.message, 'Close', {duration: 5000, panelClass: 'error-snackbar'})
        }
      })
    } else {
      this.couponForm.markAllAsTouched();
    }
  }

}
