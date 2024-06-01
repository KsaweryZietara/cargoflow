import { Button, Cell, Column, Flex, Row, TableBody, TableHeader, TableView, View } from "@adobe/react-spectrum";
import { Vehicle } from "../../types/Vehicle";
import ZoomIn from "@spectrum-icons/workflow/ZoomIn";
import Delete from "@spectrum-icons/workflow/Delete";
import Draw from "@spectrum-icons/workflow/Draw";

interface VehiclesListProps {
    vehicles: Vehicle[];
    onVehicleAdd: () => void;
    onVehicleDelete: (id: number) => void;
    onVehicleEdit: (vehicle: Vehicle) => void;
    onVehicleView: (vehicle: Vehicle) => void;
}

const columns = [
    { name: "Type", uid: "type" },
    { name: "Engine number", uid: "engineNumber" },
    { name: "Engine capacity", uid: "engineCapacity" },
    { name: "Brand", uid: "brand" },
    { name: "Maximum capacity", uid: "maximumCapacity" },
    { name: "Operational", uid: "operational" },
    { name: "Next inspection", uid: "nextTechnicalInspection" },
    { name: "Edit", uid: "edit", invisible: true, isIcon: true },
    { name: "View", uid: "view", invisible: true, isIcon: true },
    { name: "Delete", uid: "delete", invisible: true, isIcon: true },
];

export default function VehiclesList({
    vehicles,
    onVehicleAdd,
    onVehicleDelete,
    onVehicleEdit,
    onVehicleView,
}: VehiclesListProps) {
    return (
        <View padding={"80px"}>
            <View paddingY={"20px"} width={"100%"}>
                <Flex justifyContent={"end"}>
                    <Button onPress={onVehicleAdd} variant="accent">
                        Add vehicle
                    </Button>
                </Flex>
            </View>
            <TableView flex density="spacious">
                <TableHeader columns={columns}>
                    {(column) => <Column key={column.uid} width={column.isIcon ? 40 : null}>{!column.invisible && column.name}</Column>}
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
                                                onVehicleEdit(vehicle);
                                            }}
                                            variant="accent"
                                        >
                                            <Draw />
                                        </Button>
                                    </Cell>,
                                    <Cell>
                                        <Button
                                            onPress={() => {
                                                onVehicleView(vehicle);
                                            }}
                                            variant="accent"
                                        >
                                            <ZoomIn />
                                        </Button>
                                    </Cell>,
                                    <Cell>
                                        <Button
                                            onPress={() => {
                                                onVehicleDelete(vehicle.id);
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
