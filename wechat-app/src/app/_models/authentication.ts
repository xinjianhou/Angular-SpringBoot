export class Authentication {
  username: string;
  loginTime: number;
  token: string;

  constructor(
    username: string,
    token: string,
    loginTime?: number,
  ) {
    this.username = username;
    this.token = token;
    this.loginTime = loginTime;
  }
}
