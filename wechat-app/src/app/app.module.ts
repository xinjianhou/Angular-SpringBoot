import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { NgxPopperModule } from 'ngx-popper';
import { NgbActiveModal, NgbModule, } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService, UserService, StorageService, VideoService } from './_services';
import { AuthenticationInterceptor } from './_interceptor/authentication.interceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from './alert/alert.component';
import {  HomeModule } from './home/home.module';
import { BsModalService, ComponentLoaderFactory, ModalBackdropComponent, PositioningService } from 'ngx-bootstrap';
import { ModalContainerComponent } from 'ngx-bootstrap/modal';
import { UserIdleModule } from 'angular-user-idle';
import { ConfirmComponent } from './confirm/confirm.component';
import { HomeComponent } from './home/home.component';

@NgModule({
  // 声明视图类，组件，指令以及管道。
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    AlertComponent,
    ModalContainerComponent,
    ModalBackdropComponent,
    ConfirmComponent,

  ],
  // declarations的子集，可用于其他模块的组件模版。
  exports: [ ],
  // 本模块声明的组件的模版类所在的模块
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
    UserIdleModule.forRoot({idle: 600, timeout: 300, ping: 120})
  ],
  entryComponents: [AlertComponent, ModalContainerComponent, ModalBackdropComponent, ConfirmComponent, HomeComponent],
  // 服务的创建者，加入到全局服务列表中，可用于应用任何部分。
  providers: [
    NgbModal,
    StorageService,
    VideoService,
    BsModalService,
    ComponentLoaderFactory,
    PositioningService,
    NgbActiveModal,
    {provide: 'authService', useClass: AuthenticationService},
    {provide: 'userService', useClass: UserService},
   // {provide: 'storageService', useClass: StorageService},
    {provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi: true},
   // {provide: BASE_URL, useValue: 'http://localhost:80/WeChat'},
  ],
  // 组件主视图，根组件
  bootstrap: [AppComponent]
})
export class AppModule {
}
