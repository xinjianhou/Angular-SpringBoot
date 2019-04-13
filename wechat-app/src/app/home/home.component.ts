import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { AuthenticationModel } from '../_models';
import { StorageService } from '../_services';
import { WelcomeComponent } from './welcome/welcome.component';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';
import { ConfirmComponent } from '../confirm/confirm.component';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  auth: AuthenticationModel;
  currentPage: string;
  time = 0;
  session_time: number;
  modalRef: BsModalRef;
  config = {
    ignoreBackdropClick: true,
  };

  @ViewChild(WelcomeComponent)
  welcomePage!: WelcomeComponent;

  constructor(private storage: StorageService, private modalService: BsModalService,
              @Inject('authService') private authService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.auth = this.storage.getAuth();
    this.currentPage = 'welcome';
    this.session_time = environment.expiration;
    if (this.auth) {
      this.startTimer();
    }

    // this.route.params.forEach((params: Params) => {
    //     const filter = params['filter'];
    //     this.filterPages(filter);
    //   }
    // );
  }

  changePage(page: string): void {
    // this.resetTimer();
    this.filterPages(page);

  }

  private startTimer(): void {
    setInterval(() => {
      this.time++;
      if (this.time === (this.session_time - 40)) {
        this.modalRef = this.modalService.show(ConfirmComponent, this.config);
        this.modalRef.content.title = 'Confirm';
        this.modalRef.content.message = `Session time up, Do you want to expand?`;
        this.modalRef.content.confirmBtn = 'Yes';
        this.modalRef.content.cancelBtn = 'No';
        this.modalService.onHidden.subscribe((r: string) => {
          if (this.modalRef.content.returnVal) {
            console.log('yes');
            this.resetTimer();
          } else {
            this.logout();
          }
        });
      } else if (this.time === (this.session_time - 10)) {
        this.logout();
      }
    }, 1000);

  }

  private logout(): void {
    this.modalRef.hide();
    this.authService.logout();
    this.router.navigate(['login']).catch(err => {
      console.log(err);
    });
  }

  private resetTimer(): void {
    this.time = 0;
  }

  filterPages(filter: string): void {
    if (filter === this.currentPage) {
      return;
    } else {
      if (this.router.navigate([filter])) {
        this.currentPage = filter;
        // this.router.navigate([`home/${filter}`]);
      }
    }
  }
}
