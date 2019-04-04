import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login';
import { RegisterComponent } from './register';


const routes: Routes = [
  {path: '', redirectTo: 'home/welcome', pathMatch: 'full'},
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'welcome', redirectTo: 'home/welcome'},
  // otherwise redirect to home
  {path: '**', redirectTo: 'home/welcome'},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule {
}
