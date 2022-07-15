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

  scheduledForArray = [
    'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'
  ];
  // scheduledForArray = [
  //   {name:'Monday'},{name:'Tuesday'},{name:'Wednesday'},{name:'Thursday'},{name:'Friday'}
  //   ,{name:'Saturday'},{name:'Sunday'}
  // ];

  getStatusIdFromName(name: string) {
    let returnArray = this.statusArray.filter((x) => {
      return (name === x.name)
    });
    return returnArray[0].id;
  }

  getStatusArray() {
    return this.statusArray;
  }
  getSchedulerForArray() {
    return this.scheduledForArray;
  }

  getScheduleForArrayFromString(scheduledfor: string) {
    return scheduledfor.split(",");
  }

  getStringFromScheduleForArray(scheduledforArray: any[]) {
    console.log(scheduledforArray.toString);
    return scheduledforArray.toString();
  }




}
