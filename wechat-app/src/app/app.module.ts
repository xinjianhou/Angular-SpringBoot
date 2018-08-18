import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login';
import { RegisterComponent } from './register';
import { NgxPopperModule } from 'ngx-popper';
import { NgbModule, } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService, UserService, StorageService, VideoService } from './_services';
import { AuthenticationInterceptor } from './_interceptor/authentication.interceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from './alert/alert.component';
import { HomeModule } from './home';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    AlertComponent,
  ],
  imports: [
    NgbModule.forRoot(),
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPopperModule,
    HttpClientModule,
    HomeModule,
  ],
  entryComponents: [AlertComponent],
  providers: [
    UserService,
    AuthenticationService,
    NgbModal,
    StorageService,
    VideoService,
    [{provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi: true}],
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
