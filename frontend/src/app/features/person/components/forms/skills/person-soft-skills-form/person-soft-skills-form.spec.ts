import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonSoftSkillsForm } from './person-soft-skills-form.js';

describe('PersonSoftSkillsFormTs', () => {
  let component: PersonSoftSkillsForm;
  let fixture: ComponentFixture<PersonSoftSkillsForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonSoftSkillsForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonSoftSkillsForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
