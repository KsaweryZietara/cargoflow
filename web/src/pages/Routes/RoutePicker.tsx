import { Button, Flex, View, TableView, TableHeader, TableBody, Column, Cell, Row } from "@adobe/react-spectrum";
import { Route } from "../../types/Routes";
import AddCircle from "@spectrum-icons/workflow/AddCircle";
import { useEffect, useState } from "react";

interface RoutesListProps {
    routes: Route[];
    onRouteSelected: (route: Route) => void;
    defaultValue?: Route;
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
    { name: "Pick", uid: "pick", invisible: true, align: "end", isIcon: true },
];

export default function RoutesList({ routes, onRouteSelected, defaultValue }: RoutesListProps) {
    const [selectedRoute, setSelectedRoute] = useState<Route>();

    useEffect(() => {
        if (defaultValue) {
            setSelectedRoute(defaultValue);
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
                                                setSelectedRoute(route);
                                                onRouteSelected(route);
                                            }}
                                            variant={selectedRoute?.id === route.id ? "accent" : "negative"}
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
