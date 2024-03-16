import {Injectable} from '@angular/core';

const TOKEN = 'Gym-token'
const USER = 'Gym-user'

@Injectable({
  providedIn: 'root'
})
export class UserStorageService {

  constructor() {
  }

  public saveToken(token: string): void {
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN, token);
  }

  public saveUser(user): void {
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER, JSON.stringify(user));
  }

  static getToken(): string {
    return localStorage.getItem(TOKEN);
  }

  static getUser(): any {
    return JSON.parse(localStorage.getItem(USER));
  }

  static getUserId(): string {
    const user = this.getUser();
    if (user == null) {
      return ""
    }
    return user.userID;
  }

  static getUserRike(): string {
    const user = this.getUser();
    if (user == null) {
      return ""
    }
    return user.role;
  }

  static isAdminLoggedIn(): boolean {
    if (this.getToken() === null) {
      return false
    }
    const role: string = this.getUserRike();
    return role == 'ADMIN';
  }

  static isCustomerLoggedIn(): boolean {
    if (this.getToken() === null) {
      return false
    }
    const role: string = this.getUserRike();
    return role == 'CUSTOMER';
  }

  static logout():void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }

}
