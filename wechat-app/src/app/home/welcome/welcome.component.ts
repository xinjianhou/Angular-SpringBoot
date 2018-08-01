import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UserService, VideoService} from '../../_services';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbDatepickerConfig} from '@ng-bootstrap/ng-bootstrap';
import {DateUtil} from '../../_utils';
import {WindowService} from '@progress/kendo-angular-dialog';
import {WindowComponent} from '../../window';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss'],
  providers: [NgbDatepickerConfig],
})
export class WelcomeComponent implements OnInit {

  homeForm: FormGroup;

  videoSource: string;

  @ViewChild('videoPlayer') videoPlayer: ElementRef;

  toggleVideo() {

    this.videoPlayer.nativeElement.paused ? this.videoPlayer.nativeElement.pause() : this.videoPlayer.nativeElement.play();
  }

  constructor(private userService: UserService, private formBuilder: FormBuilder, config: NgbDatepickerConfig,
              private windowService: WindowService, private videoSevice: VideoService, private sanitizer: DomSanitizer) {
    config.maxDate = DateUtil.formatDate(new Date(1990, 1, 1));
    config.maxDate = DateUtil.formatDate(new Date(2099, 11, 31));
    config.outsideDays = 'collapsed';

  }

  ngOnInit() {
    this.buildForm();
    this.f.searchDate.setValue(DateUtil.formatDate());
    this.videoSevice.getVideo('home').subscribe(res => {
      // this.videoPlayer.nativeElement.srcObject = res;
      // const fileReader = new FileReader();
      //
      // fileReader.readAsDataURL(res);

      const objURL = URL.createObjectURL(res);
      console.log(objURL);

      this.videoSource = objURL;
    });
  }

  onSubmit(): void {

    console.log(this.f.searchDate.value);

  }

  get f() {
    return this.homeForm.controls;
  }

  buildForm(): void {
    this.homeForm = this.formBuilder.group({
        searchDate: ['', [Validators.required]]

      }
    );
  }

  public showWindow() {
    const windowRef = this.windowService.open({
        title: 'Success!',
        content: WindowComponent,
        keepContent: false,
        width: 800,
        height: 650,
        minWidth: 500,
        minHeight: 300,
        draggable: true,
        resizable: true

      })
    ;

    const userInfo = windowRef.content.instance;
    userInfo.name = 'admin';
    userInfo.age = 42;
  }

}
