import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ChatUpdateComponent } from 'app/entities/chat/chat-update.component';
import { ChatService } from 'app/entities/chat/chat.service';
import { Chat } from 'app/shared/model/chat.model';

describe('Component Tests', () => {
  describe('Chat Management Update Component', () => {
    let comp: ChatUpdateComponent;
    let fixture: ComponentFixture<ChatUpdateComponent>;
    let service: ChatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ChatUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ChatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Chat('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Chat();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
