import {Component} from '@angular/core';
import {AdminService} from "../../service/admin.service";
import {Coupon} from "../../../interface/GlobalTypes";

@Component({
  selector: 'app-coupons',
  templateUrl: './coupons.component.html',
  styleUrls: ['./coupons.component.css']
})
export class CouponsComponent {

  coupons: Coupon[];

  constructor(private adminService: AdminService) {
  }

  ngOnInit(){
    this.getCoupons();
  }

  getCoupons() {
    this.adminService.getCoupons().subscribe(res => {
      this.coupons = res;
    })
  }
}
