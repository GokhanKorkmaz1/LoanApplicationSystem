import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { take } from 'rxjs';
import { CreditResponse } from '../models/Credit/CreditResponse';
import { AlertifyService } from '../services/alertify.service';
import { CreditService } from '../services/credit.service';

@Component({
  selector: 'app-credit',
  templateUrl: './credit.component.html',
  styleUrls: ['./credit.component.css']
})
export class CreditComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private creditService: CreditService, private alertifyService: AlertifyService) { }
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
      this.creditService.getCredit(this.identityNumber, this.birthdate).pipe(take(1)).subscribe(data =>
        this.creditResponse = data, err => {
          this.alertifyService.error("Müşteri Bulunamadı!");
          console.error(err.error);
        });
    } else Object.keys(this.creditForm.controls).forEach(key => this.creditForm.controls[key].markAsDirty());
  }

  readValuesFromForm = () => {
    if (this.creditForm.valid) {
      this.identityNumber = this.creditForm.get('identityNumber')?.value;
      let birthday = new Date(this.creditForm.get('birthdate')?.value);
      this.birthdate = `${birthday.getFullYear()}-${birthday.getMonth() + 1}-${birthday.getDate()}`;
    }
  }

}
