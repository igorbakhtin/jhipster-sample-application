import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IChat } from 'app/shared/model/chat.model';

type EntityResponseType = HttpResponse<IChat>;
type EntityArrayResponseType = HttpResponse<IChat[]>;

@Injectable({ providedIn: 'root' })
export class ChatService {
  public resourceUrl = SERVER_API_URL + 'api/chats';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/chats';

  constructor(protected http: HttpClient) {}

  create(chat: IChat): Observable<EntityResponseType> {
    return this.http.post<IChat>(this.resourceUrl, chat, { observe: 'response' });
  }

  update(chat: IChat): Observable<EntityResponseType> {
    return this.http.put<IChat>(this.resourceUrl, chat, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IChat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChat[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
