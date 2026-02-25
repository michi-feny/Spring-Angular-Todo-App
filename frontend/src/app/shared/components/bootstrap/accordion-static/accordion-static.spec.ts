import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgbdAccordionStatic } from './accordion-static';

describe('AccordionStatic', () => {
  let component: NgbdAccordionStatic;
  let fixture: ComponentFixture<NgbdAccordionStatic>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NgbdAccordionStatic]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NgbdAccordionStatic);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
