import { Employee } from "../types/Employee";
import { Job } from "../types/Jobs";
import { Vehicle } from "../types/Vehicle";
import { parseDateTime, CalendarDateTime } from "@internationalized/date";

export function filterEmployeesAndVehicles(
    allJobs: Job[],
    vehicles: Vehicle[],
    employees: Employee[],
    dateStart: CalendarDateTime,
    dateEnd: CalendarDateTime
) {
    if (dateStart === null || dateEnd === null || dateStart.compare(dateEnd) >= 0)
        return { unOccupiedEmployees: [], unOccupiedVehicles: [] };

    const filteredJobs = allJobs.filter((job) => !job.completed);
    const jobsInThisTime = filteredJobs.filter((job) => ifPeriodBlocksJob(dateStart, dateEnd, job));
    const { unOccupiedEmployees, unOccupiedVehicles } = filterOccupiedEmployeesAndVehicles(
        employees,
        vehicles,
        jobsInThisTime
    );

    return { unOccupiedEmployees, unOccupiedVehicles };
}

function filterOccupiedEmployeesAndVehicles(employees: Employee[], vehicles: Vehicle[], jobs: Job[]) {
    const unOccupiedEmployees = [...employees];
    const unOccupiedVehicles = [...vehicles];

    jobs.forEach((job) => {
        const jobVehicleId = job.vehicle.id;
        const jobEmployeeId = job.employee.id;

        deleteItemFromArray(unOccupiedVehicles, jobVehicleId);
        deleteItemFromArray(unOccupiedEmployees, jobEmployeeId);
    });

    return { unOccupiedEmployees, unOccupiedVehicles };
}

function ifPeriodBlocksJob(dateStart: CalendarDateTime, dateEnd: CalendarDateTime, job: Job) {
    const departureDate = parseDateTime(job.departureDate);
    const arrivalDate = parseDateTime(job.arrivalDate);

    const cStartDeparture = dateStart.compare(departureDate);
    const cStartArrival = dateStart.compare(arrivalDate);
    const cEndDeparture = dateEnd.compare(departureDate);
    const cEndArrival = dateEnd.compare(arrivalDate);

    if (cStartDeparture <= 0 && cEndDeparture > 0 && cEndArrival <= 0) {
        return true;
    } else if (cStartDeparture >= 0 && cEndArrival <= 0) {
        return true;
    } else if (cStartDeparture >= 0 && cStartArrival < 0 && cEndDeparture >= 0) {
        return true;
    } else if (cStartDeparture < 0 && cEndArrival > 0) {
        return true;
    } else {
        return false;
    }
}

function deleteItemFromArray<T extends { id: number }>(array: T[], id: number) {
    const index = array.findIndex((item) => item.id === id);
    if (index > -1) array.splice(index, 1);
}
