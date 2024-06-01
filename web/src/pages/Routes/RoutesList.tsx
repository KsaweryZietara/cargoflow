import { Button, Flex, View, TableView, TableHeader, TableBody, Column, Cell, Row } from "@adobe/react-spectrum";
import { Route } from "../../types/Routes";
import Delete from "@spectrum-icons/workflow/Delete";

interface RoutesListProps {
    routes: Route[];
    onRouteAdd: () => void;
    onRouteDelete: (id: number) => void;
}

interface routeColumn {
    name: string;
    uid: string;
    align?: "start" | "end" | "center";
    invisible?: boolean;
    isIcon?: boolean;
}

const columns: routeColumn[] = [
    { name: "Departure City", uid: "departureCity" },
    { name: "Arrival City", uid: "arrivalCity" },
    { name: "Distance", uid: "distance" },
    { name: "Delete", uid: "delete", invisible: true, align: "end", isIcon: true },
];

export default function RoutesList({ routes, onRouteAdd, onRouteDelete }: RoutesListProps) {
    return (
        <View padding={"80px"}>
            <View paddingY={"20px"} width={"100%"}>
                <Flex justifyContent={"end"}>
                    <Button onPress={onRouteAdd} variant="accent">
                        Add route
                    </Button>
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
                    {routes.map((route) => {
                        const cells = [];
                        for (const [key, value] of Object.entries(route)) {
                            let cell;
                            if (key === "id") {
                                continue;
                            } else if (key === "departureCity" || key === "arrivalCity") {
                                cell = <Cell>{value.name + ' - ' + value.address}</Cell>;
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
                                                onRouteDelete(route.id);
                                            }}
                                            variant="accent"
                                        >
                                            <Delete/>
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
