import {Component, OnInit} from '@angular/core';
import {Authentication} from '../_models';
import {StorageService} from '../_services';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  auth: Authentication;

  constructor(private storage: StorageService) {
  }

  ngOnInit() {
    this.auth = this.storage.getAuth();
  }

}
