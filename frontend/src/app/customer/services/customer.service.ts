import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserStorageService} from "../../services/storage/user-storage.service";


const BASE_URL = "http://localhost:8080/"

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) {
  }


  getAllProducts(): Observable<any> {
    return this.http.get(BASE_URL + "api/customer/products", {
      headers: this.createAuthHeader(),
    })
  }

  getAllProductsByName(name: string): Observable<any> {
    return this.http.get(BASE_URL + `api/customer/search/${name}`, {
      headers: this.createAuthHeader(),
    })
  }

  addToCart(productId: any): Observable<any> {
    const cartDTO = {
      productId: productId,
      userId: UserStorageService.getUserId()
    }
    return this.http.post(BASE_URL + `api/customer/cart`, cartDTO, {headers: this.createAuthHeader(),})
  }

  getCartByUserId(): Observable<any> {
    const userId = UserStorageService.getUserId();
    return this.http.get(BASE_URL + `api/customer/cart/${userId}`, {headers: this.createAuthHeader(),})
  }

  private createAuthHeader(): HttpHeaders {
    return new HttpHeaders().set("Authorization", 'Bearer ' + UserStorageService.getToken());
  }
}
