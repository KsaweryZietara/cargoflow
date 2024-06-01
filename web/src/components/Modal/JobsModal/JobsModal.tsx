import { useEffect, useState } from "react";
import { Job } from "../../../types/Jobs";
import { parseDateTime } from "@internationalized/date";
import { createJob, updateJob } from "../../../api/Job";
import Modal from "../Modal";
import { Button, ButtonGroup, TextField, Form, Flex, DatePicker } from "@adobe/react-spectrum";
import { Route } from "../../../types/Routes";
import { Vehicle } from "../../../types/Vehicle";
import { Employee } from "../../../types/Employee";
import RoutePicker from "../../../pages/Routes/RoutePicker";
import EmployeePicker from "../../../pages/Employees/EmployeesPicker";
import { VehiclesPicker } from "../../../pages/Vehicles/VehiclesPicker";
import { getDateNow } from "../../../utils/timeParser";
import { PostJob } from "../../../api/Job";
import { useUnOccupiedEntites } from "./useUnOccupiedEntites";

interface JobsModalProps {
    allJobs: Job[];
    job?: Job;
    routes: Route[];
    vehicles: Vehicle[];
    employees: Employee[];
    isOpen: boolean;
    readOnly: boolean;
    onClose: () => void;
    onJobEdit: (job: Job) => void;
    onJobAdd: (job: Job) => void;
}

export function JobsModal({
    allJobs,
    job,
    routes,
    vehicles,
    employees,
    isOpen,
    readOnly,
    onClose,
    onJobEdit,
    onJobAdd,
}: JobsModalProps) {
    const [_departureDate, setDepartureDate] = useState(parseDateTime(job?.departureDate ?? getDateNow()));
    const [_arrivalDate, setArrivalDate] = useState(parseDateTime(job?.arrivalDate ?? getDateNow()));

    const [routeId, setRouteId] = useState<number>();
    const [employeeId, setEmployeeId] = useState<number>();
    const [vehicleId, setVehicleId] = useState<number>();

    readOnly = (readOnly || job?.completed) ?? false;

    const { unOccupiedEmployees, unOccupiedVehicles } = useUnOccupiedEntites(
        readOnly,
        allJobs,
        employees,
        vehicles,
        _departureDate,
        _arrivalDate
    );

    useEffect(() => {
        setRouteId(job?.route.id);
        setEmployeeId(job?.employee.id);
        setVehicleId(job?.vehicle.id);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const onFormSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.currentTarget;
        const departureDate: string = _departureDate.toString();
        const arrivalDate: string = _arrivalDate.toString();
        const cargoDescription: string = form.cargoDescription.value;

        if (!employeeId || !vehicleId || !routeId) return;

        const jobValue: PostJob = {
            departureDate,
            arrivalDate,
            cargoDescription,
            employeeId,
            vehicleId,
            routeId,
        };

        if (job?.id) {
            updateJob(job.id, jobValue).then((job) => {
                onJobEdit(job);
            });
        } else {
            createJob(jobValue).then((job) => {
                onJobAdd(job);
            });
        }

        onClose();
    };

    return (
        <Modal isOpen={isOpen} onClose={onClose} wide={true}>
            <div className="employee-modal-wrapper">
                <Form
                    autoComplete="off"
                    isReadOnly={readOnly || job?.completed}
                    onSubmit={onFormSubmit}
                    validationBehavior="native"
                    position={"relative"}
                >
                    <DatePicker label="Departure Date" value={_departureDate} onChange={setDepartureDate} isRequired />
                    <DatePicker label="Arrival Date" value={_arrivalDate} onChange={setArrivalDate} isRequired />
                    <TextField
                        label="Description"
                        name="cargoDescription"
                        maxLength={20}
                        defaultValue={job?.cargoDescription}
                        isRequired
                    />
                    <EmployeePicker
                        employees={unOccupiedEmployees}
                        onEmployeeSelect={(employee) => {
                            setEmployeeId(employee.id);
                        }}
                        defaultValue={job?.employee}
                    />
                    <RoutePicker
                        routes={routes}
                        onRouteSelected={(route) => {
                            setRouteId(route.id);
                        }}
                        defaultValue={job?.route}
                    />
                    <VehiclesPicker
                        vehicles={unOccupiedVehicles.filter((vehicle) => vehicle.operational)}
                        onVehicleSelected={(vehicle) => {
                            setVehicleId(vehicle.id);
                        }}
                        defaultValue={job?.vehicle}
                    />
                    <ButtonGroup marginTop={"single-line-height"}>
                        <Flex justifyContent={"center"} width={"100%"}>
                            <Button isDisabled={readOnly} type="submit" variant="accent" width={"200px"}>
                                Change
                            </Button>
                        </Flex>
                    </ButtonGroup>
                </Form>
            </div>
        </Modal>
    );
}
