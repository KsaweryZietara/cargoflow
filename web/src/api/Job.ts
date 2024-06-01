import { Job } from "../types/Jobs";
import { api } from "./Api";

export interface PostJob {
    departureDate: string;
    arrivalDate: string;
    cargoDescription: string;
    employeeId: number;
    vehicleId: number;
    routeId: number;
}

export type PutJob = PostJob;

interface CompletedJob {
    actualDepartureDate: string;
    actualArrivalDate: string;
    comments: string;
}

export async function getJobs(): Promise<Job[]> {
    const response = await api.get("driver-routes");
    return response.data;
}

export async function createJob(job: PostJob): Promise<Job> {
    const response = await api.post("driver-routes", job);
    return response.data;
}

export async function deleteJob(id: number) {
    const response = await api.delete(`driver-routes/${id}`);
    return response;
}

export async function updateJob(id: number, job: PutJob): Promise<Job> {
    const response = await api.put(`driver-routes/${id}`, job);
    return response.data;
}

export async function completeJob(id: number, job: CompletedJob): Promise<Job> {
    const response = await api.put(`driver-routes/${id}/complete`, job);
    return response.data;
}
