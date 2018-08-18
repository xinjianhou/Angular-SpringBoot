import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { WindowModule } from '@progress/kendo-angular-dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WindowComponent } from '../window';
import { WelcomeComponent } from './welcome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HomeRoutingModule } from './home-routing.module';
import { LinkComponent } from './link/link.component';
import { ButtonModule, CoreModule, InputModule } from 'truly-ui';


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
  ],
  declarations: [
    HomeComponent,
    WindowComponent,
    WelcomeComponent,
    LinkComponent,

  ],
})
export class HomeModule {
}
