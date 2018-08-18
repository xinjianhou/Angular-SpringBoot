import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {Authentication, User} from '../_models';
import {catchError, tap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {ErrorUtil} from '../_utils';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {StorageService} from './storage.service';


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  url: string;

  constructor(private http: HttpClient, private storage: StorageService) {
    this.url = environment.apiUrl + environment.auth;
  }

  checkByUsername(username: string): Observable<User> {

    return this.http.post<any>(this.url + '/checkUserByUsername', JSON.stringify({username: username}), httpOptions).pipe(
      tap(_ => console.log(`fetched User username=${username}`)),
      catchError(ErrorUtil.handleError<User>(`checkByUsername username=${username}`))
    );

  }

  login(user: User): Observable<boolean> {
    return this.http.post<any>(this.url + '/login', JSON.stringify(user), httpOptions).pipe(
      tap(response => {
        if (response) {
          // store username and jwt token in local storage to keep user logged in between page refreshes
          return this.storage.putAuth(response);
        } else{
          return false;
        }
      }),
      catchError(ErrorUtil.handleError<Authentication>(`login username=${user.username}, password=${user.password}`))
    );
  }

  getToken(): String {
    return this.storage.getAuth() ? this.storage.getAuth().token : null;
  }

  logout(): void {
    // clear token remove user from local storage to log user out
    this.storage.removeAuth();
  }

  isLoggedIn(): boolean {
    const token: String = this.getToken();
    return token && token.length > 0;
  }
}
