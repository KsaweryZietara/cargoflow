import { useEffect, useState } from "react";
import { deleteJob, getJobs } from "../../api/Job";
import { Job } from "../../types/Jobs";
import JobsList from "./JobsList";
import { EmployeeEditModal } from "../../components/Modal/EmployeeModal/EmployeeEditModal";
import { VehiclesModal } from "../../components/Modal/VehiclesModal/VehiclesModal";
import { JobsModal } from "../../components/Modal/JobsModal/JobsModal";
import { Route } from "../../types/Routes";
import { Vehicle } from "../../types/Vehicle";
import { getRoutes } from "../../api/Routes";
import { getVehicles } from "../../api/Vehicles";
import { getEmployees } from "../../api/Employees";
import { Employee } from "../../types/Employee";
import { EmployeeReportModal } from "../../components/Modal/EmployeeModal/EmployeeReportModal";

export default function Jobs() {
    const [jobs, setJobs] = useState<Job[]>([]);
    const [selectedJob, setSelectedJob] = useState<Job>();
    const [modalMode, setModalMode] = useState<"closed" | "default" | "viewJob" | "viewEmployee" | "viewTruck">(
        "closed"
    );
    const [routes, setRoutes] = useState<Route[]>([]);
    const [vehicles, setVehicles] = useState<Vehicle[]>([]);
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [isReportOpened, setIsReportOpened] = useState(false);

    const onEmployeeView = (job: Job) => {
        setSelectedJob(job);
        setModalMode("viewEmployee");
    };

    const onVehicleView = (job: Job) => {
        setSelectedJob(job);
        setModalMode("viewTruck");
    };

    const onJobAdd = () => {
        setModalMode("default");
    };

    const onJobDelete = (id: number) => {
        deleteJob(id);
        setJobs(jobs.filter((job) => job.id !== id));
    };

    const onJobEdit = (job: Job) => {
        setSelectedJob(job);
        setModalMode("default");
    };

    const onJobView = (job: Job) => {
        setSelectedJob(job);
        setModalMode("viewJob");
    };

    const onUserReport = () => {
        setIsReportOpened(true);
    };

    useEffect(() => {
        // ! Server tutaj nie wyrabiał
        getJobs().then((jobs) => {
            setJobs(jobs);
            getRoutes().then((routes) => {
                setRoutes(routes);
                getVehicles().then((vehicles) => {
                    setVehicles(vehicles);
                    getEmployees().then((employees) => {
                        setEmployees(employees);
                    });
                });
            });
        });
        // getRoutes().then((routes) => {
        //     setRoutes(routes);
        // });
        // getVehicles().then((vehicles) => {
        //     setVehicles(vehicles);
        // });
        // getEmployees().then((employees) => {
        //     setEmployees(employees);
        // });
    }, []);

    return (
        <>
            {isReportOpened && (
                <EmployeeReportModal
                    isOpen={isReportOpened}
                    onClose={() => {
                        setIsReportOpened(false);
                    }}
                    employees={employees}
                    vehicles={vehicles}
                />
            )}
            {modalMode === "viewEmployee" && selectedJob?.employee && (
                <EmployeeEditModal
                    readOnly={true}
                    employee={selectedJob?.employee}
                    isOpen={modalMode === "viewEmployee"}
                    onClose={() => {
                        setSelectedJob(undefined);
                        setModalMode("closed");
                    }}
                    onEmployeeEdit={() => {}}
                />
            )}
            {modalMode === "viewTruck" && selectedJob?.vehicle && (
                <VehiclesModal
                    readOnly={true}
                    vehicle={selectedJob?.vehicle}
                    isOpen={modalMode === "viewTruck"}
                    onClose={() => {
                        setModalMode("closed");
                        setSelectedJob(undefined);
                    }}
                    onVehicleAdd={() => {}}
                    onVehicleEdit={() => {}}
                />
            )}
            {modalMode === "default" && (
                <JobsModal
                    allJobs={jobs}
                    job={selectedJob}
                    routes={routes}
                    vehicles={vehicles}
                    employees={employees}
                    isOpen={modalMode === "default"}
                    readOnly={false}
                    onClose={() => {
                        setModalMode("closed");
                        setSelectedJob(undefined);
                    }}
                    onJobAdd={(job) => {
                        setJobs([...jobs, job]);
                    }}
                    onJobEdit={(job) => {
                        const filteredJobs = jobs.filter((jobs) => jobs.id != selectedJob?.id);
                        setJobs([...filteredJobs, job]);
                    }}
                />
            )}
            <JobsList
                jobs={jobs}
                onJobAdd={onJobAdd}
                onJobDelete={onJobDelete}
                onJobEdit={onJobEdit}
                onJobView={onJobView}
                onEmployeeView={onEmployeeView}
                onVehicleView={onVehicleView}
                onUserReport={onUserReport}
            />
        </>
    );
}
