import { Routes } from "@angular/router";
import { CreditComponent } from "./credit/credit.component";
import { CustomerRegisterComponent } from "./customer-register/customer-register.component";

export const appRoutes: Routes = [
    { path: "customer", component: CustomerRegisterComponent },
    { path: "credit", component: CreditComponent },
    { path: "**", redirectTo: "credit", pathMatch: "full" }
];