export interface IMessage {
  id?: string;
  text?: string;
  chatId?: string;
}

export class Message implements IMessage {
  constructor(public id?: string, public text?: string, public chatId?: string) {}
}
