import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, pipe} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {ErrorUtil} from '../_utils';
import {Authentication} from '../_models';



const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = environment.apiUrl + environment.video;
  }

  getVideo(name: string): Observable<Blob> {
    return this.http.get(this.url  + name,
      {
        responseType: 'blob'
      }).pipe(
      tap(res => {
        return res;
      }),

      catchError(ErrorUtil.handleError<Blob>(`get video ${name}`))
    );

  }

  searchVideo(name: string): Observable<any> {
    return this.http.post(this.url + '/searchVideo', JSON.stringify({name: name}), httpOptions).pipe(
      tap(res => {
        return res;
      }),
      catchError(ErrorUtil.handleError<Authentication>(`search video ${name}`))
    );
  }

}
