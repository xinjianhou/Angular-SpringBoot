import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeComponent } from './welcome/welcome.component';
import { LinkComponent } from './link/link.component';
import { TodoComponent } from './todo/todo.component';
import { HomeComponent } from './home.component';
import { KnowledgeComponent } from './knowledge/knowledge.component';

const routes: Routes = [
  {
    path: 'home', component: HomeComponent,
    children: [
      {path: 'welcome', component: WelcomeComponent},
      {path: 'link', component: LinkComponent},
      {path: 'todo', component: TodoComponent},
      {path: 'knowledge', component: KnowledgeComponent},
      ]
  },

];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
  ],
  declarations: [],
  exports: [RouterModule],
})
export class HomeRoutingModule {
}
