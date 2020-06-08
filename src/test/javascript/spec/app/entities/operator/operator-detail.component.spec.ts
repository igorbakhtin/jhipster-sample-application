import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OperatorDetailComponent } from 'app/entities/operator/operator-detail.component';
import { Operator } from 'app/shared/model/operator.model';

describe('Component Tests', () => {
  describe('Operator Management Detail Component', () => {
    let comp: OperatorDetailComponent;
    let fixture: ComponentFixture<OperatorDetailComponent>;
    const route = ({ data: of({ operator: new Operator('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [OperatorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OperatorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OperatorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load operator on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.operator).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
