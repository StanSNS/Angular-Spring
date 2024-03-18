import {ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot} from '@angular/router';
import {UserStorageService} from "../storage/user-storage.service";

export const authGuardGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const adminRouts: string[] = ['/admin/dashboard', '/admin/category', '/admin/product', '/admin/orders', '/admin/post-coupon', '/admin/coupons']
    const customerRouts: string[] = ['/customer/dashboard', '/customer/cart']

    if (state.url === "/login" || state.url === "/register") {
      return !UserStorageService.isAdminLoggedIn() || !UserStorageService.isCustomerLoggedIn();

    } else if (adminRouts.includes(state.url)) {
      return UserStorageService.isAdminLoggedIn();

    } else if (customerRouts.includes(state.url)) {
      return UserStorageService.isCustomerLoggedIn();

    }
    return false;
  }
;
