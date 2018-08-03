import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbDatepickerConfig} from '@ng-bootstrap/ng-bootstrap';
import {DateUtil} from '../../_utils';

@Component({
  selector: 'app-link',
  templateUrl: './link.component.html',
  styleUrls: ['./link.component.scss']
})
export class LinkComponent implements OnInit {

  linkForm: FormGroup;

  constructor(private formBuilder: FormBuilder, config: NgbDatepickerConfig) {
    config.maxDate = DateUtil.formatDate(new Date(1990, 1, 1));
    config.maxDate = DateUtil.formatDate(new Date(2099, 11, 31));
    config.outsideDays = 'collapsed';
  }

  ngOnInit() {
    this.buildForm();
    this.f.searchDate.setValue(DateUtil.formatDate());
  }

  buildForm(): void {
    this.linkForm = this.formBuilder.group({
        searchDate: ['', [Validators.required]]

      }
    );
  }
  get f() {
    return this.linkForm.controls;
  }
}
