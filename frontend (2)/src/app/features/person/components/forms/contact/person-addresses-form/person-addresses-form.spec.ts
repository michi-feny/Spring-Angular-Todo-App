import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonAddressesForm } from './person-addresses-form';

describe('PersonAddressesForm', () => {
  let component: PersonAddressesForm;
  let fixture: ComponentFixture<PersonAddressesForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonAddressesForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonAddressesForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
