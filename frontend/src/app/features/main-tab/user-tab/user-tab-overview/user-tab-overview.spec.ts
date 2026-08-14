import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserTabOverview } from './user-tab-overview';

describe('UserTabOverview', () => {
  let component: UserTabOverview;
  let fixture: ComponentFixture<UserTabOverview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserTabOverview]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserTabOverview);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
