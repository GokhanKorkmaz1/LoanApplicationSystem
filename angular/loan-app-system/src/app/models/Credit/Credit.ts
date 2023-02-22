import { Customer } from "../Customer/Customer";

export class Credit {
    id: number;
    state:boolean;
    amount:number;
    customerId:number;
    
    customer:Customer;
}