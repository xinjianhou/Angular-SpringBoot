import { Directive, ElementRef, Input } from '@angular/core';
import * as Inputmask from 'inputmask';

@Directive({
  selector: '[appRestInput]'
})
export class RestInputDirective {


  private regexMap = {
    integer: '^[0-9]*$',
    float: '^[+-]?([0-9]*[.])?[0-9]+$',
    words: '([A-z]*\\s)*',
    point25: '^\-?[0-9]*(?:\\.25|\\.50|\\.75|)$',
    date: '^[0-9]{2}[/][0-9]{2}[/][0-9]{4}$'
  };


  constructor(private el: ElementRef) {
  }

  @Input('appRestInput')
  public set defineInputType(type: string) {
    Inputmask({regex: this.regexMap[type], placeholder: ''}).mask(this.el.nativeElement);
  }
}
