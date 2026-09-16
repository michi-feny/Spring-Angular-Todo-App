import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonNationalitiesForm } from './person-nationalities-form';

describe('PersonNationalitiesForm', () => {
  let component: PersonNationalitiesForm;
  let fixture: ComponentFixture<PersonNationalitiesForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonNationalitiesForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonNationalitiesForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
