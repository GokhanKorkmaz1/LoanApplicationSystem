import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreditResponse } from '../models/Credit/CreditResponse';

@Injectable({
  providedIn: 'root'
})
export class CreditService {
  apiPath: string = "http://localhost:8081/api/v1/credits";

  constructor(private httpClient: HttpClient) { }

  private getHttp = <T>(functionName: string): Observable<T> =>
    this.httpClient.get<T>(this.apiPath + functionName);

  private postHttp<T>(functionName: string, param: any): Observable<T> {
    return this.httpClient.post<T>(this.apiPath + functionName, param);
  }

  getCredit = (identityNumber: string, birthdate: string): Observable<CreditResponse> =>
    this.getHttp("?identityNumber=" + identityNumber + "&birthdate=" + birthdate);

}
