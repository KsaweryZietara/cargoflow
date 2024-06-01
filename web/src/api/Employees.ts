import { Employee } from "../types/Employee";
import { api } from "./Api";

export async function getEmployees() {
    const response = await api.get<Employee[]>("employees");
    return response.data;
}

export interface PostUser {
    name: string;
    surname: string;
    pesel: string;
    birthDate: string;
    login: string;
    password: string;
    positionId: number;
    driverLicensesIds: number[];
}

export interface PutUser {
    name: string;
    surname: string;
    pesel: string;
    birthDate: string;
    positionId: number;
    driverLicensesIds: number[];
}

export async function createEmployee(employee: PostUser) : Promise<Employee> {
    const response = await api.post("employees", employee)
    return response.data;
}

export async function deleteEmployee(id: number) {
    const response = await api.delete(`employees/${id}`);
    return response;
}

export async function updateEmployee(id:number, employee: PutUser) : Promise<Employee> {
    const response = await api.put(`employees/${id}`, employee);
    return response.data;
}

export async function getPositions() {
    const response = await api.get("positions");
    return response.data;
}
