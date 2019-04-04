export class User {
  username: string;
  password: string;
  email: string;
  enabled: boolean;


  constructor(username: string, password: string, email?: string, enabled?: boolean,) {

    this.username = username;
    this.password = password;
    this.email = email;
    this.enabled = enabled;
  }

}
