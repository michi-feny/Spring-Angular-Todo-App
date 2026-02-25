import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPasswordFlow } from './reset-password-flow';

describe('ResetPasswordFlow', () => {
  let component: ResetPasswordFlow;
  let fixture: ComponentFixture<ResetPasswordFlow>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResetPasswordFlow]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResetPasswordFlow);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
