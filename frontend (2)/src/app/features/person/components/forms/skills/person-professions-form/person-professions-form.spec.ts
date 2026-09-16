import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonProfessionsForm } from './person-professions-form';

describe('PersonProfessionsForm', () => {
  let component: PersonProfessionsForm;
  let fixture: ComponentFixture<PersonProfessionsForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonProfessionsForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonProfessionsForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
