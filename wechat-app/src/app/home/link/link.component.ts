import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { DateUtil } from '../../_utils';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { FileService } from '../../_services';

@Component({
  selector: 'app-link',
  templateUrl: './link.component.html',
  styleUrls: ['./link.component.scss']
})
export class LinkComponent implements OnInit {

  linkForm: FormGroup;
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = {percentage: 0};

  file: File;

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  constructor(private formBuilder: FormBuilder, config: NgbDatepickerConfig, private fileService: FileService) {
    config.maxDate = DateUtil.formatDate(new Date(1990, 1, 1));
    config.maxDate = DateUtil.formatDate(new Date(2099, 11, 31));
    config.outsideDays = 'collapsed';
  }

  ngOnInit() {
    this.buildForm();
    this.f.searchDate.setValue(DateUtil.formatDate());
  }

  buildForm(): void {
    this.linkForm = this.formBuilder.group({
        searchDate: ['', [Validators.required]]

      }
    );
  }

  get f() {
    return this.linkForm.controls;
  }

  onSubmit(): void {

    console.log(this.f.searchDate.value);

  }

  upload() {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    this.fileService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File is completely uploaded!');
      }
    });

    this.selectedFiles = undefined;
  }

  uploadDocument(event) {
    this.file = event.target.files[0];
    const fileReader = new FileReader();
    fileReader.onload = (e) => {
      console.log(fileReader.result);
    };
    fileReader.readAsText(this.file);
  }

}
