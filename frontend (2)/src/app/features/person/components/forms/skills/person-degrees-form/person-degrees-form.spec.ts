import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonDegreesForm } from './person-degrees-form';

describe('PersonDegreesForm', () => {
  let component: PersonDegreesForm;
  let fixture: ComponentFixture<PersonDegreesForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonDegreesForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonDegreesForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
