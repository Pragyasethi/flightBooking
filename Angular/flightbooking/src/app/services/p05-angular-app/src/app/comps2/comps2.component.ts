import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-comps2',
  templateUrl: './comps2.component.html',
  styleUrls: ['./comps2.component.css']
})
export class Comps2Component implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  name:String ="Pragya";

  updateName(n:String):void{
    console.log("New name is "+n);
    this.name=n;

  }


}
