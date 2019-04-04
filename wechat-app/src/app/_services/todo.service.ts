import { Injectable } from '@angular/core';
import { UUID } from 'angular2-uuid';
import { AuthenticationModel, Todo } from '../_models';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ErrorUtil } from '../_utils';


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = environment.apiUrl + environment.todo;
  }


  addTodo(todoIteam: string): Observable<Todo[]> {
    const todo: Todo = {
      id: UUID.UUID(),
      desc: todoIteam,
      completed: false,
    };
    return this.http.post<Todo[]>(this.url, JSON.stringify(todo), httpOptions).pipe(
      tap(_ => console.log(`add todo =${todo}`)),
      catchError(ErrorUtil.handleError<Todo[]>(`todo update failed!`))
    );
  }

  getTodos(): Observable<Todo[]> {
    return this.http.get<Todo[]>(this.url, httpOptions).pipe(
      catchError(ErrorUtil.handleError<Todo[]>(`get todos failed!`))
    );
  }

  deleteTodo(todo: Todo | string): Observable<Todo[]> {

    const id = typeof todo === 'string' ? todo : todo.id;
    const url = this.url + '/' + id;
    return this.http.delete<Todo[]>(url, httpOptions).pipe(
      tap(_ => console.log(`delete todo id=${todo}`)),
      catchError(ErrorUtil.handleError<Todo[]>(`delete todo failed!`))
    );
  }

  toogleTodo(todo: Todo): Observable<Todo> {
    return this.http.put<Todo>(this.url, JSON.stringify(todo), httpOptions).pipe(
      tap(_ => console.log(`update todo = ${todo}`)),
      catchError(ErrorUtil.handleError<Todo>(`update todo failed!`))
    );
  }

  toggleAll(status: boolean): Observable<Todo[]> {
    return this.http.put<Todo[]>(this.url + '/all', status, httpOptions).pipe(
      tap(_ => console.log(`update todos complete status = ${status}`)),
      catchError(ErrorUtil.handleError<Todo[]>(`update todos complete ${status} failed!`))
    );
  }

  clearCompeted(): Observable<Todo[]> {
   return this.http.delete<Todo[]>(this.url + '/completed', httpOptions).pipe(
      tap(_ => console.log(`delete todos witch already competed`)),
      catchError(ErrorUtil.handleError<Todo[]>(`delete todo failed!`))
    );
  }

}
