import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {UserStorageService} from "../storage/user-storage.service";

const BASE_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private userStorageService: UserStorageService) {
  }

  register(registerDTO: any): Observable<any> {
    return this.http.post(BASE_URL + "register", registerDTO);
  }

  login(email: string, password: string): any {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = {email, password};

    return this.http.post(BASE_URL + 'login', body, {headers, observe: 'response'}).pipe(
      map((res) => {
        const token = res.headers.get('authorization').substring(7);
        const user = res.body;

        if (token && user) {
          this.userStorageService.saveToken(token);
          this.userStorageService.saveUser(user);
          return true;
        }
        return false
      })
    );
  }
}
