import { useEffect, useState } from "react";
import { DriverLicence, Employee, Position } from "../../../types/Employee";
import LicensePicker from "../../LicensePicker/LicensePicker";
import Modal from "../Modal";
import { Button, ButtonGroup, TextField, Form, View, Flex, Picker, Item, DatePicker } from "@adobe/react-spectrum";
import { PutUser, getPositions, updateEmployee } from "../../../api/Employees";
import { parseDate } from "@internationalized/date";
import "./EmployeeModal.scss";
import { dateParse, getDateNow } from "../../../utils/timeParser";
import { isValidPesel } from "../../../utils/isValidPesel";

interface EmployeeEditModalProps {
    employee: Employee;
    isOpen: boolean;
    readOnly: boolean;
    onClose: () => void;
    onEmployeeEdit: (employee: Employee) => void;
}

export function EmployeeEditModal({ employee, isOpen, readOnly, onClose, onEmployeeEdit }: EmployeeEditModalProps) {
    const [licenses, setLicenses] = useState<DriverLicence[]>([]);
    const [positions, setPositions] = useState<Position[]>([]);
    const [date, setDate] = useState(parseDate(employee.birthDate ?? getDateNow()));

    const onFormSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const id = employee.id;
        const form = e.currentTarget;
        const name: string = form.name.value;
        const surname: string = form.surname.value;
        const pesel: string = form.pesel.value;
        const birthDate: string = dateParse(date.day, date.month, date.year);
        const positionId = form.position.value;
        const driverLicensesIds = licenses.map((license) => license.id);

        const employeeData: PutUser = {
            name,
            surname,
            pesel,
            birthDate,
            positionId,
            driverLicensesIds,
        };

        updateEmployee(id, employeeData).then((employee) => {
            onEmployeeEdit(employee);
        });

        onClose();
    };

    useEffect(() => {
        getPositions().then((positions) => {
            setPositions(positions);
        });
        setLicenses(employee?.driverLicenses ?? []);
    }, []);

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <div className="employee-modal-wrapper">
                <Form isReadOnly={readOnly} autoComplete="off" onSubmit={onFormSubmit} validationBehavior="native" position={"relative"}>
                    <Picker isDisabled={readOnly} name="position" label="Position" isRequired defaultSelectedKey={`${employee?.position.id}`}>
                        {positions.map((position) => (
                            <Item key={position.id}>{position.name}</Item>
                        ))}
                    </Picker>
                    <TextField label="Name" name="name" defaultValue={employee?.name} isRequired />
                    <TextField label="Surname" name="surname" defaultValue={employee?.surname} isRequired />
                    <DatePicker label="Date" value={date} onChange={setDate} isRequired />
                    <TextField
                        label="Pesel"
                        name="pesel"
                        isRequired
                        defaultValue={employee?.pesel}
                        validate={(value: string) => {
                            const result = isValidPesel(value);
                            return result ? true : "Invalid pesel";
                        }}
                    />
                    <LicensePicker
                        isViewOnly={readOnly}
                        licenses={licenses}
                        setLicenses={(licenses) => {
                            setLicenses(licenses);
                        }}
                    />
                    <ButtonGroup marginTop={"single-line-height"}>
                        <Flex justifyContent={"center"} width={"100%"}>
                            <Button isDisabled={readOnly} type="submit" variant="accent" width={"200px"}>
                                Change
                            </Button>
                        </Flex>
                    </ButtonGroup>
                </Form>
            </div>
        </Modal>
    );
}
