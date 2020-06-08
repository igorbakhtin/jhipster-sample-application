import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'chat',
        loadChildren: () => import('./chat/chat.module').then(m => m.JhipsterSampleApplicationChatModule),
      },
      {
        path: 'message',
        loadChildren: () => import('./message/message.module').then(m => m.JhipsterSampleApplicationMessageModule),
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.JhipsterSampleApplicationClientModule),
      },
      {
        path: 'operator',
        loadChildren: () => import('./operator/operator.module').then(m => m.JhipsterSampleApplicationOperatorModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterSampleApplicationEntityModule {}
