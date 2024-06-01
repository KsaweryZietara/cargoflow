import { Route } from "../types/Routes";
import { api } from "./Api";

interface PostRoute {
    departureCityId: number;
    arrivalCityId: number;
    distance: number;
}

export async function getRoutes(): Promise<Route[]> {
    const response = await api.get("routes");
    return response.data;
}

export async function deleteRoute(id: number) {
    const response = await api.delete(`routes/${id}`);
    return response;
}

export async function addRoute(route: PostRoute): Promise<Route> {
    const response = await api.post("routes", route);
    return response.data;
}
