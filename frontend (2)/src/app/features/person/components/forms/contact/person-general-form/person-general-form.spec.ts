import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonGeneralForm } from './person-general-form';

describe('PersonGeneralForm', () => {
  let component: PersonGeneralForm;
  let fixture: ComponentFixture<PersonGeneralForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonGeneralForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonGeneralForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
