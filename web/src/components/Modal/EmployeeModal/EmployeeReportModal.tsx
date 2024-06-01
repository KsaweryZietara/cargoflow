import Modal from "../Modal";
import "./EmployeeModal.scss";
import EmployeePicker from "../../../pages/Employees/EmployeesPicker";
import { Employee } from "../../../types/Employee";
import { useEffect, useState } from "react";
import { DatePicker, Form } from "@adobe/react-spectrum";
import { getDateNow } from "../../../utils/timeParser";
import { parseDateTime } from "@internationalized/date";
import { getKilometersDrivenByDriver, getKilometersDrivenByVehicle } from "../../../api/Reports";
import { VehiclesPicker } from "../../../pages/Vehicles/VehiclesPicker";
import { Vehicle } from "../../../types/Vehicle";

interface EmployeeReportModalProps {
    isOpen: boolean;
    onClose: () => void;
    employees: Employee[];
    vehicles: Vehicle[];
}

export function EmployeeReportModal({ isOpen, onClose, employees, vehicles }: EmployeeReportModalProps) {
    const [employeeId, setEmployeeId] = useState<number>();
    const [vehicleId, setVehicleId] = useState<number>();
    const [distance, setDistance] = useState<number>();
    const [distanceVehicle, setDistanceVehicle] = useState<number>();
    const [startDate, setStartDate] = useState(parseDateTime(getDateNow()));
    const [endDate, setEndDate] = useState(parseDateTime(getDateNow()));
    useEffect(() => {
        if (employeeId) {
            getKilometersDrivenByDriver(employeeId, startDate.toString(), endDate.toString()).then((data) =>
                setDistance(data)
            );
        }
    }, [employeeId, endDate, startDate]);
    useEffect(() => {
        if (vehicleId) {
            getKilometersDrivenByVehicle(vehicleId, startDate.toString(), endDate.toString()).then((data) =>
                setDistanceVehicle(data)
            );
        }
    }, [endDate, startDate, vehicleId]);

    return (
        <Modal isOpen={isOpen} onClose={onClose} wide>
            <div className="employee-modal-wrapper">
                <Form autoComplete="off" position={"relative"}>
                    <DatePicker label="Departure Date" value={startDate} onChange={setStartDate} isRequired />
                    <DatePicker label="Arrival Date" value={endDate} onChange={setEndDate} isRequired />
                    <EmployeePicker
                        employees={employees}
                        onEmployeeSelect={(employee) => {
                            setEmployeeId(employee.id);
                        }}
                    />
                    <div>
                        <span style={{ fontSize: "20px" }}>
                            Kilometers driven by this employee: <b>{distance}</b>
                        </span>
                    </div>
                    <VehiclesPicker
                        vehicles={vehicles}
                        onVehicleSelected={(vehicle) => {
                            setVehicleId(vehicle.id);
                        }}
                    />
                    <div>
                        <span style={{ fontSize: "20px" }}>
                            Kilometers driven by this vehicle: <b>{distanceVehicle}</b>
                        </span>
                    </div>
                </Form>
            </div>
        </Modal>
    );
}
