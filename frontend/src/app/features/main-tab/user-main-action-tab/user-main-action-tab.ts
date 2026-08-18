import { Component } from '@angular/core';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { UserTabOverview } from '../user-tab/user-tab-overview/user-tab-overview';

@Component({
  selector: 'app-user-main-action-tab',
  imports: [
    TabsModule,
    UserTabOverview
  ],
  templateUrl: './user-main-action-tab.html',
  styleUrl: './user-main-action-tab.css',
})
export class UserMainActionTab {

}
