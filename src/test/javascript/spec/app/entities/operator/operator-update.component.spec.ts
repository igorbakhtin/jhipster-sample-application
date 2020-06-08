import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OperatorUpdateComponent } from 'app/entities/operator/operator-update.component';
import { OperatorService } from 'app/entities/operator/operator.service';
import { Operator } from 'app/shared/model/operator.model';

describe('Component Tests', () => {
  describe('Operator Management Update Component', () => {
    let comp: OperatorUpdateComponent;
    let fixture: ComponentFixture<OperatorUpdateComponent>;
    let service: OperatorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [OperatorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OperatorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OperatorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OperatorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Operator('123');
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
        const entity = new Operator();
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
