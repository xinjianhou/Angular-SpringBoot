export class UploadedFile {
  id: number;
  fileName: string;
  fileSize: number;
  uploadDate: string;
  location: string;

  constructor(id?: number, name?: string, size?: number, uploadDate?: string, location?: string) {
    this.id = id;
    this.fileName = name;
    this.fileSize = size;
    this.uploadDate = uploadDate;
    this.location = location;
  }
}
