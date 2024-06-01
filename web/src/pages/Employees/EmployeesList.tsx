import { Button, Cell, Column, Flex, Row, TableBody, TableHeader, TableView, View } from "@adobe/react-spectrum";
import { DriverLicence, Employee } from "../../types/Employee";
import ZoomIn from "@spectrum-icons/workflow/ZoomIn";
import Delete from "@spectrum-icons/workflow/Delete";
import Draw from "@spectrum-icons/workflow/Draw";

interface EmployeesListProps {
    employees: Employee[];
    onEmployeeAdd: () => void;
    onEmployeeDelete: (id: number) => void;
    onEmployeeEdit: (employee: Employee) => void;
}

const columns = [
    { name: "Name", uid: "name" },
    { name: "Surname", uid: "surname" },
    { name: "Pesel", uid: "pesel" },
    { name: "Birthdate", uid: "birthDate" },
    { name: "Position", uid: "position" },
    { name: "DriverLicense", uid: "driverLicenses" },
    { name: "Edit", uid: "edit", invisible: true, isIcon: true },
    { name: "Delete", uid: "delete", invisible: true, isIcon: true },
];

export default function EmployeesList({
    employees,
    onEmployeeAdd,
    onEmployeeDelete,
    onEmployeeEdit,
}: EmployeesListProps) {
    return (
        <View padding={"80px"}>
            <View paddingY={"20px"} width={"100%"}>
                <Flex justifyContent={"end"}>
                    <Button onPress={onEmployeeAdd} variant="accent">
                        Add employee
                    </Button>
                </Flex>
            </View>
            <TableView flex density="spacious">
                <TableHeader columns={columns}>
                    {(column) => (
                        <Column key={column.uid} width={column.isIcon ? 60 : null}>
                            {!column.invisible && column.name}
                        </Column>
                    )}
                </TableHeader>
                <TableBody>
                    {employees.map((employee) => {
                        const cells = [];
                        for (const [key, value] of Object.entries(employee)) {
                            let cell = <Cell>{value}</Cell>;

                            if (key === "id") continue;

                            if (key === "position") {
                                cell = <Cell>{value.name}</Cell>;
                            } else if (key === "driverLicenses") {
                                const licenses: string[] = [];

                                value.forEach((license: DriverLicence) => {
                                    licenses.push(license.name);
                                });

                                cell = <Cell>{licenses.join(", ")}</Cell>;
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
                                                onEmployeeEdit(employee);
                                            }}
                                            variant="accent"
                                        >
                                            <Draw/>
                                        </Button>
                                    </Cell>,
                                    <Cell>
                                        <Button
                                            onPress={() => {
                                                onEmployeeDelete(employee.id);
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
