import {AuthenticationModel} from '../_models/index';
import {environment} from '../../environments/environment';

export class StorageService {

  private storage: any;
  readonly time: number;

  constructor() {
    this.storage = localStorage;
    this.time = environment.expiration;
  }

  putAuth(auth: AuthenticationModel): boolean {
    this.storage.setItem('auth', JSON.stringify(auth));
    if (this.storage.getItem('auth')) {
      return true;
    } else {
      return false;
    }
  }

  getAuth(): AuthenticationModel {
    let auth: AuthenticationModel;
    // const newTime = new Date().getTime();
    if (this.storage.getItem('auth')) {
      auth = JSON.parse(this.storage.getItem('auth'));
      // if (newTime < (auth.loginTime + this.time * 1000)) {
      //   return auth;
      // }
    }
    return auth;
  }

  removeAuth(): void {
    this.storage.removeItem('auth');
  }
}
