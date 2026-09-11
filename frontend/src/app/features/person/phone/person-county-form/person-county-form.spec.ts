import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonCountyForm } from './person-county-form';

describe('PersonCountyForm', () => {
  let component: PersonCountyForm;
  let fixture: ComponentFixture<PersonCountyForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonCountyForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonCountyForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
