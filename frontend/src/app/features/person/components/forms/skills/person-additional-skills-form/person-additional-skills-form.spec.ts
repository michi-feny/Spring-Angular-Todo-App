import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonAdditionalSkillsForm } from './person-additional-skills-form';

describe('PersonAdditionalSkillsForm', () => {
  let component: PersonAdditionalSkillsForm;
  let fixture: ComponentFixture<PersonAdditionalSkillsForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonAdditionalSkillsForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonAdditionalSkillsForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
