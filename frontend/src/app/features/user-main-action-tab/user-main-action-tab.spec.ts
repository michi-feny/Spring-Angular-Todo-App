import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserMainActionTab } from './user-main-action-tab';

describe('UserMainActionTab', () => {
  let component: UserMainActionTab;
  let fixture: ComponentFixture<UserMainActionTab>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserMainActionTab]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserMainActionTab);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
