import { Injectable } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { AbstractControl } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class TooltipService {

  errorMessage: string;
  openedTooltip: NgbTooltip;

  constructor() {
  }

  openToolTip(control: AbstractControl, fieldName: string, tooltip: NgbTooltip, noTouched: boolean): void {
    this.errorMessage = '';
  }

  closeTooltips(...tooltips: NgbTooltip[]): void {
    tooltips.forEach(tooltip => {
      if (tooltip) {
        if (tooltip.isOpen()) {
          tooltip.close();
          this.openedTooltip = null;
        }
      }
    });
  }
}
