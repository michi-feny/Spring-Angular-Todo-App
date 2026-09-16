import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonWorkExperience } from './person-work-experience';

describe('PersonWorkExperience', () => {
  let component: PersonWorkExperience;
  let fixture: ComponentFixture<PersonWorkExperience>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonWorkExperience]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonWorkExperience);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
