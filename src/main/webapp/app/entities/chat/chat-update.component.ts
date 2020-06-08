import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChat, Chat } from 'app/shared/model/chat.model';
import { ChatService } from './chat.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { IOperator } from 'app/shared/model/operator.model';
import { OperatorService } from 'app/entities/operator/operator.service';

type SelectableEntity = IClient | IOperator;

@Component({
  selector: 'jhi-chat-update',
  templateUrl: './chat-update.component.html',
})
export class ChatUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];
  operators: IOperator[] = [];

  editForm = this.fb.group({
    id: [],
    clientId: [],
    operatorId: [],
  });

  constructor(
    protected chatService: ChatService,
    protected clientService: ClientService,
    protected operatorService: OperatorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chat }) => {
      this.updateForm(chat);

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));

      this.operatorService.query().subscribe((res: HttpResponse<IOperator[]>) => (this.operators = res.body || []));
    });
  }

  updateForm(chat: IChat): void {
    this.editForm.patchValue({
      id: chat.id,
      clientId: chat.clientId,
      operatorId: chat.operatorId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chat = this.createFromForm();
    if (chat.id !== undefined) {
      this.subscribeToSaveResponse(this.chatService.update(chat));
    } else {
      this.subscribeToSaveResponse(this.chatService.create(chat));
    }
  }

  private createFromForm(): IChat {
    return {
      ...new Chat(),
      id: this.editForm.get(['id'])!.value,
      clientId: this.editForm.get(['clientId'])!.value,
      operatorId: this.editForm.get(['operatorId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChat>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
