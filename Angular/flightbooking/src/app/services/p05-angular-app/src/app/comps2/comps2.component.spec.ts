import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Comps2Component } from './comps2.component';

describe('Comps2Component', () => {
  let component: Comps2Component;
  let fixture: ComponentFixture<Comps2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Comps2Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Comps2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
