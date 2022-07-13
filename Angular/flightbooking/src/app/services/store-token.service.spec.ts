import { TestBed } from '@angular/core/testing';

import { StoreTokenService } from './store-token.service';

describe('StoreTokenService', () => {
  let service: StoreTokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StoreTokenService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
