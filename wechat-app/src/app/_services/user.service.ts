import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserModel } from '../_models';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { ErrorUtil } from '../_utils';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class UserService {

  url: string;


  constructor(private http: HttpClient) {
    this.url = environment.apiUrl + environment.user;
  }


  getUser(id: number): Observable<UserModel> {
    return this.http.get<UserModel>(this.url + '/' + id, httpOptions);
  }

  register(user: UserModel): Observable<UserModel> {
    return this.http.post<any>(this.url, JSON.stringify(user), httpOptions).pipe(
      tap(resUser => {
        if (resUser) {
          return resUser;
        } else {
          return null;
        }

      }),
      catchError(ErrorUtil.handleError<UserModel>(`register username=${user.username}, password=${user.password}, email=${user.email}`))
    );
  }


}
