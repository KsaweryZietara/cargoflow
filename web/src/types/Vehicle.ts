export type VehicleType = "TRUCK" | "PASSENGER_CAR";

export interface Vehicle {
    id: number;
    type: VehicleType;
    engineNumber: string;
    engineCapacity: number;
    brand: string;
    maximumCapacity: number;
    operational: boolean;
    nextTechnicalInspection: string;
}
