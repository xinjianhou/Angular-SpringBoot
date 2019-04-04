import { Component, ElementRef, EventEmitter, Input, OnInit, Output } from '@angular/core';
import 'rxjs/add/observable/fromEvent';
import 'rxjs/Observable';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import { Observable } from 'rxjs/rx';

@Component({
  selector: 'app-todo-header',
  templateUrl: './todo-header.component.html',
  styleUrls: ['./todo-header.component.scss']
})
export class TodoHeaderComponent implements OnInit {

  inputValue = '';
  @Input()
  placeholder = 'what needs to be done?';
  @Input()
  delay = 300;
  @Output()
  onEnterUp = new EventEmitter<boolean>();

  @Output()
  textChanges = new EventEmitter<string>();


  constructor(private elementRef: ElementRef) {
    const event$ = Observable.fromEvent(elementRef.nativeElement, 'keyup')
      .map(() => this.inputValue).debounceTime(this.delay).distinctUntilChanged();
    event$.subscribe(input => {
      return this.textChanges.emit(input);
    });
  }

  ngOnInit() {
  }


  enterUp() {
    this.onEnterUp.emit(true);
    this.inputValue = '';
  }
}
