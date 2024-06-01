import { useEffect, useState } from "react";
import { Employee } from "../../types/Employee";
import { finishJob, getDriver, getDriverJobs } from "../../api/Driver";
import { Job } from "../../types/Jobs";
import DriverList from "../Driver/DriverList";
import { Vehicle } from "../../types/Vehicle";
import { VehiclesModal } from "../../components/Modal/VehiclesModal/VehiclesModal";

export function DriverDashboard() {
    const [driver, setDriver] = useState<Employee>();
    const [driverJobs, setDriverJobs] = useState<Job[]>([]);
    const [selectedVehicle, setSelectedVehicle] = useState<Vehicle>();

    const onJobFinish = (job: Job) => {
        const payload = {
            actualDepartureDate: job.departureDate,
            actualArrivalDate: new Date().toISOString(),
            comments: "",
        };
        finishJob(job.id, payload).then((job) => {
            const filteredJobs = driverJobs.filter((jobs) => jobs.id != job?.id);
            setDriverJobs([...filteredJobs, job]);
        });
    };

    const onVehicleView = (vehicle: Vehicle) => {
        setSelectedVehicle(vehicle);
    };

    useEffect(() => {
        getDriver().then((driver) => {
            setDriver(driver);
            getDriverJobs().then((jobs) => {
                setDriverJobs(jobs);
            });
        });
    }, []);

    return (
        <>
            {selectedVehicle && (
                <VehiclesModal
                    readOnly={true}
                    vehicle={selectedVehicle}
                    isOpen={selectedVehicle ? true : false}
                    onClose={() => {
                        setSelectedVehicle(undefined);
                    }}
                    onVehicleAdd={() => {}}
                    onVehicleEdit={() => {}}
                />
            )}
            <DriverList jobs={driverJobs} onJobFinish={onJobFinish} onVehicleView={onVehicleView} />
        </>
    );
}
