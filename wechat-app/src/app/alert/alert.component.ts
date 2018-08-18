import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent implements OnInit {

  @Input() headContent: string;

  @Input() bodyContent: string;

  constructor(public activeModal: NgbActiveModal) {
  }


  ngOnInit() {
  }

}
