import { Component, OnInit, ViewChild } from '@angular/core';
import { Authentication } from '../_models';
import { StorageService } from '../_services';
import { WelcomeComponent } from './welcome';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  auth: Authentication;
  currentPage: string;

  @ViewChild(WelcomeComponent)
  welcomePage!: WelcomeComponent;

  constructor(private storage: StorageService) {
  }

  ngOnInit() {
    this.auth = this.storage.getAuth();
    this.currentPage = 'home';
  }

  changePage(page: string): void {
    if (page === this.currentPage) {
      return;
    } else {
      this.currentPage = page;

    }
  }

}
