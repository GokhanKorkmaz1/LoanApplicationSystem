import { DecimalPipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { CustomerRequest } from '../models/Customer/CustomerRequest';
import { CustomerResponse } from '../models/Customer/CustomerResponse';
import { CustomerService } from '../services/customer.service';
declare let alertify:any;

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
    alertify.success("Kayıt İşleminiz Başarıyla Tamamlandı");
  }

  createCustomerRegisterForm = () => {
    this.customerRegisterForm = this.formBuilder.group({
      name: ["", Validators.required],
      surname: ["", Validators.required],
      identityNumber: ["", [Validators.required, Validators.minLength(11), Validators.maxLength(11)]],
      phoneNumber: ["", [Validators.required, Validators.minLength(8), Validators.maxLength(12)]],
      birthdate: ["", Validators.required],
      monthlyIncome: ["", [Validators.required, Validators.min(0)]],
      assurance: ["", Validators.min(0)]
    });
  }

  addCustomer = () => {
    if (this.customerRegisterForm.valid) {
      this.customerRequest = Object.assign({}, this.customerRegisterForm.value);

      this.customerAddSubs = this.customerService.saveCustomer(this.customerRequest).subscribe(data => {
        this.customerResponse = data;
        alertify.success("Kayıt İşleminiz Başarıyla Tamamlandı");
      } ,err => console.log(err.error));
    }
  }

  ngOnDestroy(): void {
    // this.customerAddSubs.unsubscribe();
  }

}
