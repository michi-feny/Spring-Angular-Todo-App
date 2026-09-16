import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonPhonesForm } from './person-phones-form';

describe('PersonPhonesForm', () => {
  let component: PersonPhonesForm;
  let fixture: ComponentFixture<PersonPhonesForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonPhonesForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonPhonesForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
