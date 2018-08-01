import { Component } from '@angular/core';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {CustomDateParseFormat} from './_utils';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [{provide: NgbDateParserFormatter, useClass: CustomDateParseFormat}]
})
export class AppComponent {
  title = 'WeChat';
}
