import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonMailForm } from './person-mail-form';

describe('PersonMailForm', () => {
  let component: PersonMailForm;
  let fixture: ComponentFixture<PersonMailForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonMailForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonMailForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
