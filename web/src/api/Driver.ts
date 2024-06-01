import { api } from "./Api";

export async function getDriver() {
    const response = await api.get(`employees/current`);
    return response.data;
}

export async function getDriverJobs() {
    const response = await api.get(`driver-routes/employee`);
    return response.data;
}

export async function finishJob(
    id: number,
    payload: {
        actualDepartureDate: string;
        actualArrivalDate: string;
        comments: string;
    }
) {
    const response = await api.put(`driver-routes/${id}/complete`, payload);
    return response.data;
}
