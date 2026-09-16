import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonEmailsForm } from './person-emails-form';

describe('PersonEmailsForm', () => {
  let component: PersonEmailsForm;
  let fixture: ComponentFixture<PersonEmailsForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonEmailsForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonEmailsForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
