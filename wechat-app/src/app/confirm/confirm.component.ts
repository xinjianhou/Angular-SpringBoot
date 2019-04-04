import { Component, OnInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss']
})
export class ConfirmComponent implements OnInit {

  message: string;
  title: string;
  confirmBtn: string;
  cancelBtn: string;
  returnVal: boolean;


  constructor(public bsModalRef: BsModalRef) {
  }

  ngOnInit() {
    this.returnVal = false;

  }

  private confirm(): void {
    this.returnVal = true;
    this.bsModalRef.hide();
  }

  private decline(): void {
    this.returnVal = false;
    this.bsModalRef.hide();
  }

}
