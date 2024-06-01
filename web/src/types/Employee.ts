export type PositionName = "admin" | "coordinator" | "driver" | "mechanic";

export interface Position {
    id: number;
    name: PositionName;
}

export interface DriverLicence {
    id: number;
    name: string;
}

export interface User {
    id: number;
    name: string;
    surname: string;
    pesel: string;
    birthDate: string;
    position: Position;
    driverLicenses: DriverLicence[];
}

export type Admin = User & { position: { id: number; name: "admin" } };
export type Coordinator = User & { position: { id: number; name: "coordinator" } };
export type Mechanic = User & { position: { id: number; name: "mechanic" } };
export type Driver = User & {
    position: { id: number; name: "driver" };
};

export type Employee = Admin | Coordinator | Mechanic | Driver;

export function isDriver(employee: Employee): employee is Driver {
    return employee.position.name === "driver";
}