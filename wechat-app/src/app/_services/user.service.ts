import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../_models';
import {Observable} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {ErrorUtil} from '../_utils';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class UserService {

  url: string;


  constructor(private http: HttpClient) {
    this.url = environment.apiUrl + environment.user;
  }


  getUser(id: number) {
    return this.http.get(`${environment.apiUrl}/user/getUser/` + id);
  }

  register(user: User): Observable<User> {
    return this.http.post<any>(this.url + '/createUser', JSON.stringify(user), httpOptions).pipe(
      tap(resUser => {
        if (resUser) {
          return resUser;
        } else {
          return null;
        }

      }),
      catchError(ErrorUtil.handleError<User>(`register username=${user.username}, password=${user.password}, email=${user.email}`))
    );
  }


}
