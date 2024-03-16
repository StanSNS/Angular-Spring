import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AuthService} from "../services/auth/auth.service";
import {Router} from "@angular/router";
import {UserStorageService} from "../services/storage/user-storage.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm!: FormGroup;
  hidePassword = true;

  constructor(
    private formBuilder: FormBuilder,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    private router: Router) {
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]],
    })
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  onSubmit(): void {
    const email = this.loginForm.get('email')!.value;
    const password = this.loginForm.get('password')!.value;

    this.authService.login(email, password).subscribe(
      (response) => {
        this.snackBar.open("Login Successful", 'OK', {duration: 5000});
        if(UserStorageService.isAdminLoggedIn()){
          this.router.navigateByUrl('admin/dashboard')
        }else if(UserStorageService.isCustomerLoggedIn()){
          this.router.navigateByUrl('customer/dashboard')
        }

      },
      (error) => {
        this.snackBar.open('Bad credentials', 'ERROR', {duration: 5000,});
      }
    )

  }


}
