import { Button, Cell, Column, Flex, Row, TableBody, TableHeader, TableView, View } from "@adobe/react-spectrum";
import { Vehicle } from "../../types/Vehicle";
import { useEffect, useState } from "react";
import AddCircle from "@spectrum-icons/workflow/AddCircle";

interface VehiclesListProps {
    vehicles: Vehicle[];
    onVehicleSelected: (vehicle: Vehicle) => void;
    defaultValue?: Vehicle;
}

const columns = [
    { name: "Type", uid: "type" },
    { name: "Engine number", uid: "engineNumber" },
    { name: "Engine capacity", uid: "engineCapacity" },
    { name: "Brand", uid: "brand" },
    { name: "Maximum capacity", uid: "maximumCapacity" },
    { name: "Operational", uid: "operational" },
    { name: "Next inspection", uid: "nextTechnicalInspection" },
    { name: "Pick", uid: "pick", invisible: true, isIcon: true },
];

export function VehiclesPicker({ vehicles, onVehicleSelected, defaultValue }: VehiclesListProps) {
    const [selectedVehicle, setSelectedVehicle] = useState<Vehicle>();

    useEffect(() => {
        if (defaultValue) {
            setSelectedVehicle(defaultValue);
        }
    }, [defaultValue]);

    return (
        <div
            style={{
                maxHeight: "150px",
                overflow: "scroll",
            }}
        >
            <TableView flex density="spacious">
                <TableHeader columns={columns}>
                    {(column) => (
                        <Column key={column.uid} width={column.isIcon ? 40 : null} align={column.isIcon ? "end" : undefined}>
                            {!column.invisible && column.name}
                        </Column>
                    )}
                </TableHeader>
                <TableBody>
                    {vehicles.map((vehicle) => {
                        const cells = [];
                        for (const [key, value] of Object.entries(vehicle)) {
                            let cell = <Cell>{value}</Cell>;

                            if (key === "id") {
                                continue;
                            } else if (key === "operational") {
                                cell = (
                                    <Cell>
                                        <span style={{ color: value ? "green" : "red" }}>{value ? "yes" : "no"}</span>
                                    </Cell>
                                );
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
                                                setSelectedVehicle(vehicle);
                                                onVehicleSelected(vehicle);
                                            }}
                                            variant={selectedVehicle?.id === vehicle.id ? "accent" : "negative"}
                                        >
                                            <AddCircle />
                                        </Button>
                                    </Cell>,
                                ]}
                            </Row>
                        );
                    })}
                </TableBody>
            </TableView>
        </div>
    );
}
