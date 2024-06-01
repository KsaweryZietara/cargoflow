import { Vehicle } from "../types/Vehicle";
import { api } from "./Api";
export type PostVehicle = Omit<Vehicle, "id">;
export type PutVehicle = Omit<Vehicle, "id">;

export async function getVehicles(): Promise<Vehicle[]> {
    const response = await api.get("vehicles");
    return response.data;
}

export async function createVehicle(vehicle: PostVehicle): Promise<Vehicle> {
    const response = await api.post("vehicles", vehicle);
    return response.data;
}

export async function deleteVehicle(id: number) {
    const response = await api.delete(`vehicles/${id}`);
    return response;
}

export async function updateVehicle(id: number, vehicle: PutVehicle): Promise<Vehicle> {
    const response = await api.put(`vehicles/${id}`, vehicle);
    return response.data;
}
