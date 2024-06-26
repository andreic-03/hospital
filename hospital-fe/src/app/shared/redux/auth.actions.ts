export class Login {
  static readonly type = '[Auth] Login';

  constructor(public username: string, public password: string) {}
}

export class Logout{
  static readonly type = '[Auth] Logout';
}

export class GetCurrentUserInfo {
  static readonly type = '[Auth] Get current user info';
}

export class GetMedicInfo {
  static readonly type = '[Auth] Get current medic info';

  constructor(public id: number) {}
}

export class GetPatientInfo {
  static readonly type = '[Auth] Get current patient info';

  constructor(public id: number) {}
}
