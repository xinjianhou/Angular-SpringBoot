import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { WindowModule } from '@progress/kendo-angular-dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WindowComponent } from '../window';
import { WelcomeComponent } from './welcome/welcome.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HomeRoutingModule } from './home-routing.module';
import { LinkComponent } from './link/link.component';
import { ButtonModule, CoreModule, InputModule } from 'truly-ui';
import { AlertComponent } from '../alert/alert.component';
import { RestInputDirective } from '../_directive/rest-input.directive';
import { TodoComponent } from './todo/todo.component';
import { TodoFooterComponent } from './todo/todo-footer/todo-footer.component';
import { TodoHeaderComponent } from './todo/todo-header/todo-header.component';
import { TodoService } from '../_services';


@NgModule({
  imports: [
    CommonModule,
    WindowModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule.forRoot(),
    HomeRoutingModule,
    CoreModule.forRoot({theme: 'default'}),
    InputModule,
    ButtonModule
  ],
  entryComponents: [
    WindowComponent,
    WelcomeComponent,
    AlertComponent,
  ],
  declarations: [
    HomeComponent,
    WindowComponent,
    WelcomeComponent,
    LinkComponent,
    RestInputDirective,
    TodoComponent,
    TodoFooterComponent,
    TodoHeaderComponent,
  ],

  providers: [
    {provide: 'todoService', useClass: TodoService},
  ],
})
export class HomeModule {
}
