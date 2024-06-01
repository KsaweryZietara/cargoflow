import { useEffect, useState } from "react";
import { DriverLicence, Employee, Position } from "../../../types/Employee";
import LicensePicker from "../../LicensePicker/LicensePicker";
import { Button, ButtonGroup, TextField, Form, Flex, Picker, Item, DatePicker } from "@adobe/react-spectrum";
import { createEmployee, getPositions } from "../../../api/Employees";
import { parseDate } from "@internationalized/date";

import Modal from "../Modal";
import "./EmployeeModal.scss";
import { dateParse, getDateNow } from "../../../utils/timeParser";
import { isValidPesel } from "../../../utils/isValidPesel";

interface EmployeeAddModalProps {
    isOpen: boolean;
    onClose: () => void;
    onEmployeeAdd: (employee: Employee) => void;
}

export function EmployeeAddModal({ isOpen, onClose, onEmployeeAdd }: EmployeeAddModalProps) {
    const [licenses, setLicenses] = useState<DriverLicence[]>([]);
    const [positions, setPositions] = useState<Position[]>([]);
    const [date, setDate] = useState(parseDate(getDateNow()));

    const onFormSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.currentTarget;
        const name: string = form.name.value;
        const surname: string = form.surname.value;
        const pesel: string = form.pesel.value;
        const birthDate: string = dateParse(date.day, date.month, date.year);
        const positionId = form.position.value;
        const login: string = form.login.value;
        const password: string = form.password.value;
        const driverLicensesIds = licenses.map((license) => license.id);

        const employee = {
            name,
            surname,
            pesel,
            birthDate,
            positionId,
            driverLicensesIds,
        };

        const postEmployee = {
            ...employee,
            login,
            password,
            driverRoutesIds: [],
        };

        createEmployee(postEmployee).then((employee) => {
            onEmployeeAdd(employee);
        });
        onClose();
    };

    useEffect(() => {
        getPositions().then((positions) => {
            setPositions(positions);
        });
    }, []);

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <div className="employee-modal-wrapper">
                <Form autoComplete="off" onSubmit={onFormSubmit} validationBehavior="native" position={"relative"}>
                    <Picker name="position" label="Position" isRequired>
                        {positions.map((position) => (
                            <Item key={position.id}>{position.name}</Item>
                        ))}
                    </Picker>
                    <TextField label="Name" name="name" isRequired />
                    <TextField label="Surname" name="surname" isRequired />
                    <DatePicker label="Date" value={date} onChange={setDate} />
                    <TextField
                        label="Pesel"
                        name="pesel"
                        isRequired
                        validate={(value: string) => {
                            const result = isValidPesel(value);
                            return result ? true : "Invalid pesel";
                        }}
                    />
                    <TextField label="Login" name="login" isRequired />
                    <TextField label="Password" name="password" type="password" isRequired />
                    <LicensePicker
                        licenses={licenses}
                        setLicenses={(licenses) => {
                            setLicenses(licenses);
                        }}
                    />
                    <ButtonGroup marginTop={"single-line-height"}>
                        <Flex justifyContent={"center"} width={"100%"}>
                            <Button type="submit" variant="accent" width={"200px"}>
                                Add
                            </Button>
                        </Flex>
                    </ButtonGroup>
                </Form>
            </div>
        </Modal>
    );
}
