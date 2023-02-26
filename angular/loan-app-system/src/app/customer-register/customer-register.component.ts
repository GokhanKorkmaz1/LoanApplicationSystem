import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { take } from 'rxjs';
import { CustomerRequest } from '../models/Customer/CustomerRequest';
import { CustomerResponse } from '../models/Customer/CustomerResponse';
import { AlertifyService } from '../services/alertify.service';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-customer-register',
  templateUrl: './customer-register.component.html',
  styleUrls: ['./customer-register.component.css']
})
export class CustomerRegisterComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private customerService: CustomerService, private alertifyService: AlertifyService) { }

  customerRegisterForm: FormGroup;
  customerRequest: CustomerRequest = new CustomerRequest();
  customerResponse: CustomerResponse;

  ngOnInit() {
    this.createCustomerRegisterForm();
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

      this.customerService.saveCustomer(this.customerRequest).pipe(take(1)).subscribe(data => {
        this.customerResponse = data;
        this.alertifyService.success("Kayıt İşleminiz Başarıyla Tamamlandı");
      }, err => {
        err.error == "Identity number already registered in the system" ?
          this.alertifyService.warning("Bu Kimlik Numarası Sistemde Kayıtlı!") :
          this.alertifyService.warning(err.error);
      });
    }
    else Object.keys(this.customerRegisterForm.controls).forEach(key => this.customerRegisterForm.controls[key].markAsDirty());
  }

}
