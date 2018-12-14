import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { Router, NavigationEnd } from '@angular/router';
import { IAlert } from '../model/Interfaces/IAlert';
import { AlertType } from '../model/alert-type.enum';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private email: string;
  private password: string;
  private errors: IAlert[];

  constructor(private userService: UserService, private router: Router) { 
    router.events.subscribe((val) => {
      if (val instanceof NavigationEnd) {
        this.errors = [];
      }
    });
  }

  ngOnInit() {
  }

  login() {
    this.userService.getUser(this.email, this.password).subscribe(
      x => {
        this.userService.login(x.body[0]);
        this.router.navigate(['survey']);
      }, (error) => {
        switch (error.status) {
          case 403: {
              this.errors.push({message: error.statusText, type: AlertType.DANGER});
            break;
          }
          default: {
            this.errors.push({message: error.statusText, type: AlertType.DANGER});
            break;
          }
        }
      }
    );
  }

}
