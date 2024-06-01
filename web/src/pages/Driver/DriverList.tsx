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
import ZoomIn from "@spectrum-icons/workflow/ZoomIn";
import { Vehicle } from "../../types/Vehicle";
import { addZeroIfBelowTen } from "../../utils/timeParser";

interface DriverListProps {
    jobs: Job[];
    onVehicleView: (vehicle: Vehicle) => void;
    onJobFinish: (job: Job) => void;
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
    { name: "Vehicle", uid: "vehicle" },
    { name: "Departure Date", uid: "departureDate" },
    { name: "Arrival Date", uid: "arrivalDate" },
    { name: "Description", uid: "cargoDescription" },
    { name: "Finish", uid: "finish", invisible: true, align: "end" },
];

export default function DriverList({ jobs, onJobFinish, onVehicleView }: DriverListProps) {
    const mappedJobs = jobs.map((job) => ({
        id: job.id,
        cargoDescription: job.cargoDescription,
        completed: job.completed,
        route: job.route,
        vehicle: job.vehicle,
        departureDate: job.departureDate,
        arrivalDate: job.arrivalDate,
        description: job.cargoDescription,
    }));

    return (
        <View width={"100%"} maxWidth={"90%"}>
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
                            } else if (key === "vehicle") {
                                cell = (
                                    <Cell>
                                        <div style={{ display: "flex", alignItems: "center" }}>
                                            <span style={{ marginRight: "1rem" }}>{value.type}</span>
                                            <Button
                                                onPress={() => {
                                                    onVehicleView(job.vehicle);
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
                            } else if (key === "departureDate" || key === "arrivalDate") {
                                if (value == null) {
                                    cell = <Cell>{value}</Cell>;
                                } else {
                                    const date = new Date(value);
                                    const day = addZeroIfBelowTen(date.getDate());
                                    const month = addZeroIfBelowTen(date.getMonth());
                                    const year = addZeroIfBelowTen(date.getFullYear());
                                    const hour = addZeroIfBelowTen(date.getHours());
                                    const minute = addZeroIfBelowTen(date.getMinutes());

                                    cell = <Cell>{`${day}-${month}-${year}: ${hour}:${minute}`}</Cell>;
                                }
                            } else {
                                cell = <Cell>{""}</Cell>;
                            }

                            cells.push(cell);
                        }

                        return (
                            <Row>
                                {[
                                    ...cells,
                                    <Cell>
                                        {job.completed ? (
                                            <Button type="button" isDisabled variant={"overBackground"}>
                                                {"Done"}
                                            </Button>
                                        ) : (
                                            <Button
                                                onPress={() => {
                                                    onJobFinish(job);
                                                }}
                                                variant="accent"
                                            >
                                                Finish job
                                            </Button>
                                        )}
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
