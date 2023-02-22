import { CustomerResponse } from "../Customer/CustomerResponse";

export class CreditResponse {
    id: number;
    state:boolean;
    amount:number;
    customerId:number;
    
    customer:CustomerResponse;
}