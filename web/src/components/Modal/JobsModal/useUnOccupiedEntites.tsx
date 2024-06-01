import { useEffect, useMemo, useState } from "react";
import { filterEmployeesAndVehicles } from "../../../utils/filterJobs";
import { Vehicle } from "../../../types/Vehicle";
import { Employee } from "../../../types/Employee";
import { Job } from "../../../types/Jobs";
import { CalendarDateTime } from "@internationalized/date";

export const useUnOccupiedEntites = (
    isReadOnly: boolean,
    allJobs: Job[],
    employees: Employee[],
    vehicles: Vehicle[],
    _departureDate: CalendarDateTime,
    _arrivalDate: CalendarDateTime
) => {
    const [unOccupiedEmployees, setUnOccupiedEmployees] = useState<Employee[]>([]);
    const [unOccupiedVehicles, setUnOccupiedVehicles] = useState<Vehicle[]>([]);

    useEffect(() => {
        if (isReadOnly) {
            setUnOccupiedEmployees(employees);
            setUnOccupiedVehicles(vehicles);
            return;
        }

        const { unOccupiedEmployees, unOccupiedVehicles } = filterEmployeesAndVehicles(
            allJobs,
            vehicles,
            employees,
            _departureDate,
            _arrivalDate
        );
        setUnOccupiedEmployees(unOccupiedEmployees);
        setUnOccupiedVehicles(unOccupiedVehicles);
    }, [
        _departureDate,
        _arrivalDate,
        allJobs,
        vehicles,
        employees,
        setUnOccupiedEmployees,
        setUnOccupiedVehicles,
        isReadOnly,
    ]);

    return useMemo(() => ({ unOccupiedEmployees, unOccupiedVehicles }), [unOccupiedEmployees, unOccupiedVehicles]);
};
