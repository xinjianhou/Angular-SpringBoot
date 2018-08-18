import { Component, OnInit } from '@angular/core';
import { Authentication } from '../_models';
import { StorageService } from '../_services';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  auth: Authentication;
  currentPage: string;

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
