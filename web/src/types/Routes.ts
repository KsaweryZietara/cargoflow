import { City } from "./Cities";

export interface Route {
    id: number;
    departureCity: City;
    arrivalCity: City;
    distance: number;
}
