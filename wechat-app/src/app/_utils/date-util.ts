import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';

const  now = new Date();

export class DateUtil {

  static formatDate(date?: Date): NgbDateStruct {
    if (!date) {
      return {year: now.getFullYear(), month: now.getMonth() + 1, day: now.getDate()};
    } else {
      return {year: date.getFullYear(), month: date.getMonth() + 1, day: date.getDate()};
    }
  }
}
