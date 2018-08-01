import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {RouterModule, Routes} from '@angular/router';
import {WelcomeComponent} from './welcome';

const routes: Routes = [

  {path: 'video', component: WelcomeComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes),
  ],
  declarations: [],
  exports: [RouterModule],
})
export class HomeRoutingModule {
}
