import { Button, Flex, View, TableView, TableHeader, TableBody, Column, Cell, Row } from "@adobe/react-spectrum";
import { City } from "../../types/Cities";
import Delete from "@spectrum-icons/workflow/Delete";

interface CitiesListProps {
    cities: City[];
    onCityAdd: () => void;
    onCityDelete: (id: number) => void;
}

interface cityColumn {
    name: string;
    uid: string;
    align?: "start" | "end" | "center";
    invisible?: boolean;
    isIcon?: boolean;
}

const columns: cityColumn[] = [
    { name: "City", uid: "city" },
    { name: "Address", uid: "address" },
    { name: "Delete", uid: "delete", invisible: true, align: "end", isIcon: true },
];

export default function CitiesList({ cities, onCityAdd, onCityDelete }: CitiesListProps) {
    return (
        <View padding={"80px"}>
            <View paddingY={"20px"} width={"100%"}>
                <Flex justifyContent={"end"}>
                    <Button onPress={onCityAdd} variant="accent">
                        Add city
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
                    {cities.map((city) => {
                        const cells = [];
                        for (const [key, value] of Object.entries(city)) {
                            const cell = <Cell>{value}</Cell>;
                            if (key === "id") continue;
                            cells.push(cell);
                        }

                        return (
                            <Row>
                                {[
                                    ...cells,
                                    <Cell>
                                        <Button
                                            onPress={() => {
                                                onCityDelete(city.id);
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
