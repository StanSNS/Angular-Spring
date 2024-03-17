import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserStorageService} from "../../services/storage/user-storage.service";

const BASE_URL = "http://localhost:8080/"

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http:HttpClient) { }

  addCategory(categoryDTO:any): Observable<any>{
    return this.http.post(BASE_URL + "api/admin/category",categoryDTO,{
      headers: this.createAuthHeader(),
    })
  }

  getAllCategories(): Observable<any>{
    return this.http.get(BASE_URL + "api/admin",{
      headers: this.createAuthHeader(),
    })
  }

  addProduct(productDTO:any): Observable<any>{
    return this.http.post(BASE_URL + "api/admin/product",productDTO,{
      headers: this.createAuthHeader(),
    })
  }

  getAllProducts(): Observable<any>{
    return this.http.get(BASE_URL + "api/admin/products",{
      headers: this.createAuthHeader(),
    })
  }

  getAllProductsByName (name:string): Observable<any>{
    return this.http.get(BASE_URL + `api/admin/search/${name}`,{
      headers: this.createAuthHeader(),
    })
  }

  deleteProduct(productId:any): Observable<any>{
    return this.http.delete(BASE_URL + `api/admin/product/${productId}`,{
      headers: this.createAuthHeader(),
    })
  }

  addCoupon(couponDTO:any): Observable<any>{
    return this.http.post(BASE_URL + "api/admin/coupons",couponDTO,{
      headers: this.createAuthHeader(),
    })
  }

  getCoupons(): Observable<any>{
    return this.http.get(BASE_URL + "api/admin/coupons",{
      headers: this.createAuthHeader(),
    })
  }

  getPlacedOrders(): Observable<any>{
    return this.http.get(BASE_URL + "api/admin/placedOrders",{
      headers: this.createAuthHeader(),
    })
  }

  changeOrderStatus(orderId:number, status:string): Observable<any>{
    return this.http.get(BASE_URL + `api/admin/order/${orderId}/${status}`,{
      headers: this.createAuthHeader(),
    })
  }

  private createAuthHeader():HttpHeaders{
    return new HttpHeaders().set("Authorization", 'Bearer ' + UserStorageService.getToken());
  }


}
