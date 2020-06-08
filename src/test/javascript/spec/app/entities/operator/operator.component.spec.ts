import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OperatorComponent } from 'app/entities/operator/operator.component';
import { OperatorService } from 'app/entities/operator/operator.service';
import { Operator } from 'app/shared/model/operator.model';

describe('Component Tests', () => {
  describe('Operator Management Component', () => {
    let comp: OperatorComponent;
    let fixture: ComponentFixture<OperatorComponent>;
    let service: OperatorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [OperatorComponent],
      })
        .overrideTemplate(OperatorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OperatorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OperatorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Operator('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.operators && comp.operators[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
