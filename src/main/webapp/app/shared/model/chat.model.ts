import { IMessage } from 'app/shared/model/message.model';

export interface IChat {
  id?: string;
  messages?: IMessage[];
  clientId?: string;
  operatorId?: string;
}

export class Chat implements IChat {
  constructor(public id?: string, public messages?: IMessage[], public clientId?: string, public operatorId?: string) {}
}
