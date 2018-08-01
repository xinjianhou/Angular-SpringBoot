import {Authentication} from '../_models/index';
import {environment} from '../../environments/environment';

export class StorageService {

  private storage: any;
  readonly time: number;

  constructor() {
    this.storage = localStorage;
    this.time = environment.expiration;
  }

  putAuth(auth: Authentication): boolean {
    auth.loginTime = new Date().getTime();
    this.storage.setItem('auth', JSON.stringify(auth));
    if (this.storage.getItem('auth')) {
      return true;
    } else {
      return false;
    }
  }

  getAuth(): Authentication {
    let auth: Authentication;
    const newTime = new Date().getTime();
    if (this.storage.getItem('auth')) {
      auth = JSON.parse(this.storage.getItem('auth'));
      if (newTime < (auth.loginTime + this.time * 1000)) {
        return auth;
      }
    }
    return null;
  }

  removeAuth(): void {
    this.storage.removeItem('auth');
  }
}
