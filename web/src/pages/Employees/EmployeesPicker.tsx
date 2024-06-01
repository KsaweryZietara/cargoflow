import { Button, Cell, Column, Row, TableBody, TableHeader, TableView } from "@adobe/react-spectrum";
import { DriverLicence, Employee } from "../../types/Employee";
import { useEffect, useState } from "react";
import AddCircle from "@spectrum-icons/workflow/AddCircle";

const columns = [
    { name: "Name", uid: "name" },
    { name: "Surname", uid: "surname" },
    { name: "Pesel", uid: "pesel" },
    { name: "Birthdate", uid: "birthDate" },
    { name: "Position", uid: "position" },
    { name: "DriverLicense", uid: "driverLicenses" },
    { name: "Pick", uid: "pick", invisible: true, isIcon: true },
];

interface EmployeesPickerProps {
    employees: Employee[];
    onEmployeeSelect: (employee: Employee) => void;
    defaultValue?: Employee;
}

export default function EmployeePicker({ employees, onEmployeeSelect, defaultValue }: EmployeesPickerProps) {
    const [selectedEmployee, setSelectedEmployee] = useState<Employee>();
    const drivers = employees.filter((user) => user.position.name === "driver");

    useEffect(() => {
        if (defaultValue) {
            setSelectedEmployee(defaultValue);
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
                        <Column
                            key={column.uid}
                            align={column.isIcon ? "end" : undefined}
                            width={column.isIcon ? 60 : null}
                        >
                            {!column.invisible && column.name}
                        </Column>
                    )}
                </TableHeader>
                <TableBody>
                    {drivers.map((employee) => {
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
                                                setSelectedEmployee(employee);
                                                onEmployeeSelect(employee);
                                            }}
                                            variant={selectedEmployee?.id === employee.id ? "accent" : "negative"}
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
