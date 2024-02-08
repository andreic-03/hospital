export class Login {
  static readonly type = '[Auth] Login';

  constructor(public username: string, public password: string) {}
}

export class GetCurrentUserInfo {
  static readonly type = '[Auth] Get current user info';
}
