import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonPhoneForm } from './person-phone-form';

describe('PersonPhoneForm', () => {
  let component: PersonPhoneForm;
  let fixture: ComponentFixture<PersonPhoneForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonPhoneForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonPhoneForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
