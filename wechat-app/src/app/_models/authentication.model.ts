export class AuthenticationModel {
  username: string;
  token: string;

  constructor(
    username: string,
    token: string,
  ) {
    this.username = username;
    this.token = token;
  }
}
