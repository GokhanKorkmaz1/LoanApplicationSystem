import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreditService } from './services/credit.service';
import { CustomerService } from './services/customer.service';
import { NavComponent } from './nav/nav.component';
import { CustomerRegisterComponent } from './customer-register/customer-register.component';
import { CreditComponent } from './credit/credit.component';
import { appRoutes } from './routes';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    CustomerRegisterComponent,
    CreditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    ReactiveFormsModule,
    NgbModule,

  ],
  providers: [],
  bootstrap: [AppComponent, CommonModule, CreditService, CustomerService]
})
export class AppModule { }
