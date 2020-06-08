import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMessage, Message } from 'app/shared/model/message.model';
import { MessageService } from './message.service';
import { IChat } from 'app/shared/model/chat.model';
import { ChatService } from 'app/entities/chat/chat.service';

@Component({
  selector: 'jhi-message-update',
  templateUrl: './message-update.component.html',
})
export class MessageUpdateComponent implements OnInit {
  isSaving = false;
  chats: IChat[] = [];

  editForm = this.fb.group({
    id: [],
    text: [],
    chatId: [],
  });

  constructor(
    protected messageService: MessageService,
    protected chatService: ChatService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ message }) => {
      this.updateForm(message);

      this.chatService.query().subscribe((res: HttpResponse<IChat[]>) => (this.chats = res.body || []));
    });
  }

  updateForm(message: IMessage): void {
    this.editForm.patchValue({
      id: message.id,
      text: message.text,
      chatId: message.chatId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const message = this.createFromForm();
    if (message.id !== undefined) {
      this.subscribeToSaveResponse(this.messageService.update(message));
    } else {
      this.subscribeToSaveResponse(this.messageService.create(message));
    }
  }

  private createFromForm(): IMessage {
    return {
      ...new Message(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      chatId: this.editForm.get(['chatId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessage>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IChat): any {
    return item.id;
  }
}
