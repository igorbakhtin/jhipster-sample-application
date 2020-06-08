export interface IOperator {
  id?: string;
  name?: string;
}

export class Operator implements IOperator {
  constructor(public id?: string, public name?: string) {}
}
