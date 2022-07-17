import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BasicComponent } from './basic/basic.component';
import { DatabindingComponent } from './databinding/databinding.component';
import { CompsComponent } from './comps/comps.component';
import { HeaderComponent } from './comps/components/header/header.component';
import { ButtonComponent } from './comps/components/button/button.component';
import { Comps2Component } from './comps2/comps2.component';
import { Comp1Component } from './comps2/components/comp1/comp1.component';
import { Comp2Component } from './comps2/components/comp2/comp2.component';
import { UploadFileComponent } from './upload-file/upload-file.component';
@NgModule({
  declarations: [
    AppComponent,
    BasicComponent,
    DatabindingComponent,
    CompsComponent,
    HeaderComponent,
    ButtonComponent,
    Comps2Component,
    Comp1Component,
    Comp2Component,
    UploadFileComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
