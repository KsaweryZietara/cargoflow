import { Employee } from "./Employee";
import { Route } from "./Routes";
import { Vehicle } from "./Vehicle";

export interface Job {
    id: number;
    departureDate: string;
    arrivalDate: string;
    cargoDescription: string;
    employee: Employee;
    vehicle: Vehicle;
    route: Route;
    completed: boolean;
    actualDepartureDate: string;
    actualArrivalDate: string;
    comments: string;
}