import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbDatepickerConfig, NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { DateUtil } from '../../_utils';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { FileService, SearchService, TooltipService } from '../../_services';
import { SearchModel, UploadedFileModel } from '../../_models';
import { BsModalRef, BsModalService, ModalOptions } from 'ngx-bootstrap';
import { AlertComponent } from '../../alert/alert.component';

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
  results: SearchModel[];

  files: UploadedFileModel[];

  bsModalRef: BsModalRef;

  modelConf: ModalOptions = {
    ignoreBackdropClick: true,
  };

  @Input() isLoggedIn: boolean;

  @ViewChild('downloadFileLink') private downloadFileLink: ElementRef;

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  constructor(private formBuilder: FormBuilder, config: NgbDatepickerConfig,
              private fileService: FileService, private modalService: BsModalService, private tooltipService: TooltipService,
              private searchService: SearchService,) {
    config.maxDate = DateUtil.formatDate(new Date(1990, 1, 1));
    config.maxDate = DateUtil.formatDate(new Date(2099, 11, 31));
    config.outsideDays = 'collapsed';
  }

  ngOnInit() {
    this.buildForm();
    this.f.searchDate.setValue(DateUtil.formatDate());
    if (this.isLoggedIn) {
      this.getFileList();
    }
  }

  buildForm(): void {
    this.linkForm = this.formBuilder.group({
        searchDate: ['', [Validators.required]],
        keyword: ['', [Validators.required]],

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
        this.getFileList();
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

  getFileList(): void {
    this.fileService.getFiles().subscribe(
      res => {
        this.files = res;
      }
    );
  }

  async download(file: UploadedFileModel): Promise<void> {
    const blob = await this.fileService.downloadFile(file.id);
    const url = window.URL.createObjectURL(blob);

    const link = this.downloadFileLink.nativeElement;
    link.href = url;
    link.download = file.fileName;
    link.click();

    window.URL.revokeObjectURL(url);
  }

  delete(file: UploadedFileModel): void {
    this.fileService.deleteFile(file).subscribe(
      res => {
        this.getFileList();
        return res;

      }
    );

  }

  private openAlert(): void {
    this.bsModalRef = this.modalService.show(AlertComponent, this.modelConf);
    this.bsModalRef.content = 'a';

  }

  openTooltip(control: AbstractControl, value: any, tooltip: NgbTooltip, noTouched: boolean): void {

    if (!control.valid && (control.touched || noTouched)) {
      this.tooltipService.openToolTip(control, value, tooltip, noTouched);
    }
  }

  closeTooltips(...tooltips: NgbTooltip[]): void {
    this.tooltipService.closeTooltips(...tooltips);
  }

  doSearch() {
    this.searchService.doSearch(this.f.keyword.value).subscribe(rs => {
      this.results = rs;
    });
  }
}
