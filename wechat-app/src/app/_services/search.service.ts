import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { SearchModel } from '../_models';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = this.url = environment.apiUrl + environment.search;
  }

  public doSearch(keyword: string): Observable<SearchModel[]> {
    return this.http.get<SearchModel[]>(this.url + `/${keyword}`, httpOptions);
  }
}
