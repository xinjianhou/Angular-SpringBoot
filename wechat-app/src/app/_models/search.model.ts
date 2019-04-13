import { UploadedFileModel } from './uploadedFile.model';

export class SearchModel {

  file: UploadedFileModel;
  content: string;

  public SearchModel(file: UploadedFileModel, content: string) {
    this.file = file;
    this.content = content;

  }

}
