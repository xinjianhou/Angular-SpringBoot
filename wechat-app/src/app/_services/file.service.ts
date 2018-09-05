import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ErrorUtil } from '../_utils';
import { Authentication } from '../_models';
import { UploadedFile } from '../_models/uploadedFile';


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class FileService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = environment.apiUrl + environment.file;
  }

  pushFileToStorage(file: File): Observable<HttpEvent<{}>> {
    const formData: FormData = new FormData();

    formData.append('file', file);

    const req = new HttpRequest('POST', this.url + '/upload', formData, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(req);

  }

  getFiles(): Observable<any> {
    return this.http.get(this.url + '/getFiles', httpOptions).pipe(
      tap(res => {
        return res;
      }),
      catchError(ErrorUtil.handleError<any>(`get all files`))
    );
  }

  async downloadFile(id: number): Promise<Blob> {
    const file = await this.http.post<Blob>(
      this.url + '/download', id,
      {responseType: 'blob' as 'json'}).toPromise();
    return file;
  }

  deleteFile(file: UploadedFile): Observable<boolean> {
    return this.http.post(this.url + '/delete', file, httpOptions).pipe(tap(res => {
      return res;
    }), catchError(ErrorUtil.handleError<any>(`delete file ${file.fileName}`)));
  }

}