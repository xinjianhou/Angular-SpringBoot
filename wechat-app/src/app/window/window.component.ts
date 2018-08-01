import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-window',
  templateUrl: './window.component.html',
  styleUrls: ['./window.component.scss'],

})
export class WindowComponent implements OnInit {
  @Input() public name: string;

  @Input() public age: number;

  constructor() {
  }

  ngOnInit() {
  }

}
