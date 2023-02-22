import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomerRequest } from '../models/Customer/CustomerRequest';
import { CustomerResponse } from '../models/Customer/CustomerResponse';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  apiPath: string = "http://localhost:8081/api/v1/customers";

  constructor(private httpClient: HttpClient) { }

  private getHttp = <T>(functionName: string): Observable<T> =>
    this.httpClient.get<T>(this.apiPath + functionName);

  private postHttp = <T>(functionName: string, param: any): Observable<T> =>
    this.httpClient.post<T>(this.apiPath + functionName, param);


  private putHttp = <T>(functionName: string, param: any): Observable<T> =>
    this.httpClient.put<T>(this.apiPath + functionName, param);


  private deleteHttp = <T>(functionName: string): Observable<T> =>
    this.httpClient.delete<T>(this.apiPath + functionName);


  getCustomerById = (customerId: number): Observable<CustomerResponse> =>
    this.getHttp("/" + customerId);

  getCustomerByIdentityNumber = (identityNumber: string): Observable<CustomerResponse> =>
    this.getHttp("/identity/" + identityNumber);

  getAllCustomers = (): Observable<CustomerResponse[]> => this.getHttp("/getall");

  saveCustomer = (customerRequest: CustomerRequest): Observable<CustomerResponse> =>
    this.postHttp("/add", customerRequest);

  updateCustomer = (customerId: number, customerRequest: CustomerRequest): Observable<CustomerResponse> =>
    this.putHttp("/update/" + customerId, customerRequest);

  deleteCustomerById = (customerId: number): Observable<CustomerResponse> =>
    this.getHttp("/delete/" + customerId);
}
