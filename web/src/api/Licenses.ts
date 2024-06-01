import { DriverLicence } from "../types/Employee";
import { api } from "./Api";

export async function getLicenses(): Promise<DriverLicence[]> {
    const response = await api.get(`driving-licenses`);
    return response.data;
}

export async function addLicense(name: string): Promise<DriverLicence> {
    const response = await api.post(`/driving-licenses`, { name });
    return response.data;
}

export async function deleteLicense(id: number) {
    const response = await api.delete(`driving-licenses/${id}`);
    return response;
}
