import { Credit } from "../Credit/Credit";

export class CustomerResponse {
    id: number;
    identityNumber: string;
    name: string;
    surname: string;
    phoneNumber: string;
    birthdate: Date;
    monthlyIncome: number;
    creditRating: number;
    assurance: number;
}