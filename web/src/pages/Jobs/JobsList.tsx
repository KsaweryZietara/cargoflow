import {
    Button,
    Flex,
    View,
    TableView,
    TableHeader,
    TableBody,
    Column,
    Cell,
    Row,
    Checkbox,
} from "@adobe/react-spectrum";
import { Job } from "../../types/Jobs";
import Delete from "@spectrum-icons/workflow/Delete";
import ZoomIn from "@spectrum-icons/workflow/ZoomIn";
import Draw from "@spectrum-icons/workflow/Draw";
import { addZeroIfBelowTen } from "../../utils/timeParser";

interface JobsListProps {
    jobs: Job[];
    onEmployeeView: (job: Job) => void;
    onVehicleView: (job: Job) => void;
    onJobAdd: () => void;
    onJobDelete: (id: number) => void;
    onJobEdit: (job: Job) => void;
    onJobView: (job: Job) => void;
    onUserReport: () => void;
}

interface jobColumn {
    name: string;
    uid: string;
    align?: "start" | "end" | "center";
    invisible?: boolean;
    isIcon?: boolean;
}

const columns: jobColumn[] = [
    { name: "Completed", uid: "completed", invisible: true, isIcon: true },
    { name: "Route", uid: "route" },
    { name: "Employee", uid: "employee" },
    { name: "Vehicle", uid: "vehicle" },
    { name: "Departure Date", uid: "departureDate" },
    { name: "Arrival Date", uid: "arrivalDate" },
    { name: "ActualDepartureDate", uid: "actualDepartureDate" },
    { name: "ActualArrivalDate", uid: "actualArrivalDate" },
    { name: "Description", uid: "cargoDescription" },
    { name: "Edit", uid: "edit", invisible: true, align: "end", isIcon: true },
    { name: "Delete", uid: "delete", invisible: true, align: "end", isIcon: true },
];

export default function JobsList({
    jobs,
    onEmployeeView,
    onVehicleView,
    onJobAdd,
    onJobDelete,
    onJobEdit,
    onUserReport,
}: JobsListProps) {
    const mappedJobs: Job[] = jobs.map((job) => ({
        id: job.id,
        cargoDescription: job.cargoDescription,
        comments: job.comments,
        completed: job.completed,
        route: job.route,
        employee: job.employee,
        vehicle: job.vehicle,
        departureDate: job.departureDate,
        arrivalDate: job.arrivalDate,
        actualDepartureDate: job.actualDepartureDate,
        actualArrivalDate: job.actualArrivalDate,
        description: job.cargoDescription,
    }));

    return (
        <View padding={"80px"}>
            <View paddingY={"20px"} width={"100%"}>
                <Flex justifyContent={"end"}>
                    <>
                        <Button onPress={onUserReport} marginEnd={"size-300"} variant="accent">Report</Button>
                        <Button onPress={onJobAdd} variant="accent">
                            Add job
                        </Button>
                    </>
                </Flex>
            </View>
            <TableView flex density="spacious">
                <TableHeader columns={columns}>
                    {(column) => (
                        <Column key={column.uid} align={column?.align ?? "start"} width={column.isIcon ? 60 : null}>
                            {!column.invisible && column.name}
                        </Column>
                    )}
                </TableHeader>
                <TableBody>
                    {mappedJobs.map((job) => {
                        const cells = [];
                        for (const [key, value] of Object.entries(job)) {
                            let cell;
                            if (key === "id" || key === "cargoDescription" || key === "comments") {
                                continue;
                            } else if (key === "completed") {
                                cell = (
                                    <Cell>
                                        <Checkbox isSelected={value ?? false} />
                                    </Cell>
                                );
                            } else if (key === "employee") {
                                cell = (
                                    <Cell>
                                        <div style={{ display: "flex", alignItems: "center" }}>
                                            <span
                                                style={{ marginRight: "1rem" }}
                                            >{`${value.name} ${value.surname}`}</span>
                                            <Button
                                                onPress={() => {
                                                    onEmployeeView(job);
                                                }}
                                                variant="cta"
                                            >
                                                <ZoomIn />
                                            </Button>
                                        </div>
                                    </Cell>
                                );
                            } else if (key === "vehicle") {
                                cell = (
                                    <Cell>
                                        <div style={{ display: "flex", alignItems: "center" }}>
                                            <span style={{ marginRight: "1rem" }}>{value.type}</span>
                                            <Button
                                                onPress={() => {
                                                    onVehicleView(job);
                                                }}
                                                variant="cta"
                                            >
                                                <ZoomIn />
                                            </Button>
                                        </div>
                                    </Cell>
                                );
                            } else if (key === "route") {
                                const departureCity = value.departureCity.name;
                                const arrivalCity = value.arrivalCity.name;
                                cell = <Cell>{`${departureCity} -> ${arrivalCity}`}</Cell>;
                            } else if (
                                key === "departureDate" ||
                                key === "arrivalDate" ||
                                key === "actualDepartureDate" ||
                                key === "actualArrivalDate"
                            ) {
                                if (value == null) {
                                    cell = <Cell>{value}</Cell>;
                                } else {
                                    const date = new Date(value);
                                    const day = addZeroIfBelowTen(date.getDate());
                                    const month = addZeroIfBelowTen(date.getMonth() + 1);
                                    const year = addZeroIfBelowTen(date.getFullYear());
                                    const hour = addZeroIfBelowTen(date.getHours());
                                    const minute = addZeroIfBelowTen(date.getMinutes());

                                    cell = <Cell>{`${day}-${month}-${year}: ${hour}:${minute}`}</Cell>;
                                }
                            } else {
                                cell = <Cell>{value}</Cell>;
                            }

                            cells.push(cell);
                        }

                        return (
                            <Row>
                                {[
                                    ...cells,
                                    <Cell>
                                        <Button
                                            onPress={() => {
                                                onJobEdit(job);
                                            }}
                                            variant="accent"
                                        >
                                            <Draw />
                                        </Button>
                                    </Cell>,
                                    <Cell>
                                        <Button
                                            onPress={() => {
                                                onJobDelete(job.id);
                                            }}
                                            variant="accent"
                                        >
                                            <Delete />
                                        </Button>
                                    </Cell>,
                                ]}
                            </Row>
                        );
                    })}
                </TableBody>
            </TableView>
        </View>
    );
}
