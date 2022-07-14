import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor() { }

  statusArray = [
    {
      id: '0',
      name: 'InActive',
    },
    {
      id: '1',
      name: 'Active',
    },
    {
      id: '2',
      name: 'Blocked',
    },
    {
      id: '3',
      name: 'Cancelled',
    },
    {
      id: '4',
      name: 'Deleted',
    },
  ];

  getStatusIdFromName(name: string) {
    let returnArray = this.statusArray.filter((x) => {
      return (name === x.name) 
    });
    return returnArray[0].id;
  }

  getArray() {
    return this.statusArray;
  }
}
