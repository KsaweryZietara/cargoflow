import { City } from "../types/Cities";
import { api } from "./Api";

interface PostCity {
    name: string;
    address: string;
}

export async function getCities(): Promise<City[]> {
    const response = await api.get("cities");
    return response.data;
}

export async function addCity(city: PostCity): Promise<City> {
    const response = await api.post("cities", city);
    return response.data;
}

export async function deleteCity(id: number) {
    const response = await api.delete(`cities/${id}`);
    return response;
}