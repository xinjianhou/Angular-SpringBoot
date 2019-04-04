import { Component, Inject, Input, OnInit } from '@angular/core';
import { Todo } from '../../_models';
import { TodoService } from '../../_services';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.scss'],
  providers: [TodoService],
})
export class TodoComponent implements OnInit {

  @Input() hitText: string;
  @Input() isLoggedIn: boolean;
  todos: Todo[] = [];
  desc = '';
  allStatus = false;

  constructor(@Inject('todoService') private service) {

  }

  ngOnInit() {
    this.getTodos();
  }

  addTodo() {
    this.service.addTodo(this.desc).subscribe(todos => {
      this.todos = todos;
      this.desc = '';
    });
  }

  toggleTodo(todo: Todo) {
    const i = this.todos.indexOf(todo);
    this.service.toogleTodo(todo).subscribe(t => {
      this.todos.splice(i, 1, t);
    });
  }

  removeTodo(todo: Todo) {
    this.service.deleteTodo(todo).subscribe(ts => {
      this.todos = ts;
    });
  }

  getTodos() {
    this.service.getTodos().subscribe(ts => {
      this.todos = ts;
    });
  }

  toggleAll() {
    this.service.toggleAll(!this.allStatus).subscribe(ts => {
      this.todos = ts;
    });
  }

  clearCompeted() {
    this.service.clearCompeted().subscribe(ts => {
      this.todos = ts;
    });
  }

  onTextChanges(value: string) {
  this.desc = value;
  }
}
