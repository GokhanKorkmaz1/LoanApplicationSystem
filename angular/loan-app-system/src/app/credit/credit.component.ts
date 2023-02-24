import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CreditResponse } from '../models/Credit/CreditResponse';
import { CustomerResponse } from '../models/Customer/CustomerResponse';
import { CreditService } from '../services/credit.service';
declare let alertify: any;

@Component({
  selector: 'app-credit',
  templateUrl: './credit.component.html',
  styleUrls: ['./credit.component.css']
})
export class CreditComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private creditService: CreditService) { }
  creditForm: FormGroup;
  identityNumber: string;
  birthdate: string;
  creditResponse: CreditResponse;

  ngOnInit() {
    this.createCreditForm();
  }

  createCreditForm = () => {
    this.creditForm = this.formBuilder.group({
      identityNumber: ["", Validators.required],
      birthdate: ["", Validators.required],
    });
  }

  findCredit = () => {
    if (this.creditForm.valid) {
      this.readValuesFromForm();
      this.creditService.getCredit(this.identityNumber, this.birthdate).subscribe
        (data => this.creditResponse = data, err => console.log(err.error));
    } else {
      alertify.warning("Lütfen gerekli bilgileri giriniz");
    }
  }

  readValuesFromForm = () => {
    if (this.creditForm.valid) {
      this.identityNumber = this.creditForm.get('identityNumber')?.value;
      let birthday = new Date(this.creditForm.get('birthdate')?.value);
      this.birthdate = `${birthday.getFullYear()}-${birthday.getMonth() + 1}-${birthday.getDate()}`;
    }
  }

}
