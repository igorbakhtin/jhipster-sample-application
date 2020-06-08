import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOperator } from 'app/shared/model/operator.model';
import { OperatorService } from './operator.service';

@Component({
  templateUrl: './operator-delete-dialog.component.html',
})
export class OperatorDeleteDialogComponent {
  operator?: IOperator;

  constructor(protected operatorService: OperatorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.operatorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('operatorListModification');
      this.activeModal.close();
    });
  }
}
