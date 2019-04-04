export class Authentication {
  username: string;
  loginTime: number;
  token: string;

  constructor(
    username: string,
    token: string,
  ) {
    this.username = username;
    this.token = token;
  }
}
