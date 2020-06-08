export interface IClient {
  id?: string;
  name?: string;
}

export class Client implements IClient {
  constructor(public id?: string, public name?: string) {}
}
