import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { CustomerRequest } from '../models/Customer/CustomerRequest';
import { CustomerResponse } from '../models/Customer/CustomerResponse';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-customer-register',
  templateUrl: './customer-register.component.html',
  styleUrls: ['./customer-register.component.css']
})
export class CustomerRegisterComponent implements OnInit, OnDestroy {

  constructor(private formBuilder: FormBuilder, private customerService: CustomerService) { }

  customerRegisterForm: FormGroup;
  customerRequest: CustomerRequest = new CustomerRequest();
  customerResponse: CustomerResponse;
  customerAddSubs: Subscription;

  ngOnInit() {
    this.createCustomerRegisterForm();
  }

  createCustomerRegisterForm = () => {
    this.customerRegisterForm = this.formBuilder.group({
      name: ["", Validators.required],
      surname: ["", Validators.required],
      identityNumber: ["", Validators.required],
      phoneNumber: ["", Validators.required],
      birthdate: ["", Validators.required],
      monthlyIncome: ["", Validators.required],
      assurance: [""]
    });
  }

  addCustomer = () => {
    if (this.customerRegisterForm.valid) {
      this.customerRequest = Object.assign({}, this.customerRegisterForm.value);

      this.customerAddSubs = this.customerService.saveCustomer(this.customerRequest).subscribe(data => {
        this.customerResponse = data;

      });
    }
  }
  

  ngOnDestroy(): void {
    // this.customerAddSubs.unsubscribe();
  }

}
