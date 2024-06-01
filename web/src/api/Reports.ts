import { api } from "./Api";

export async function getKilometersDrivenByVehicle(
    vehicleId: number,
    startDate: string,
    endDate: string
): Promise<number> {
    const response = await api.get(`/driver-routes/report/vehicle/${vehicleId}`, {
        params: {
            startDate,
            endDate,
        },
    });
    return response.data;
}

export async function getKilometersDrivenByDriver(
    driverId: number,
    startDate: string,
    endDate: string
): Promise<number> {
    const response = await api.get(`/driver-routes/report/employee/${driverId}`, {
        params: {
            startDate,
            endDate,
            id: driverId
        },
    });
    return response.data;
}
