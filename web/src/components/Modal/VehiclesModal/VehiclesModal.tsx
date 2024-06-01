import { useState } from "react";
import { Vehicle, VehicleType } from "../../../types/Vehicle";
import { parseDate } from "@internationalized/date";
import { PutVehicle, createVehicle, updateVehicle } from "../../../api/Vehicles";
import Modal from "../Modal";
import {
    Button,
    ButtonGroup,
    TextField,
    Form,
    View,
    Flex,
    Picker,
    Item,
    DatePicker,
    NumberField,
    Checkbox,
} from "@adobe/react-spectrum";
import { dateParse, getDateNow } from "../../../utils/timeParser";

interface VehiclesModalProps {
    vehicle?: Vehicle;
    isOpen: boolean;
    readOnly: boolean;
    onClose: () => void;
    onVehicleEdit: (employee: Vehicle) => void;
    onVehicleAdd: (employee: Vehicle) => void;
}

export function VehiclesModal({
    vehicle,
    isOpen,
    readOnly,
    onClose,
    onVehicleAdd,
    onVehicleEdit,
}: Readonly<VehiclesModalProps>) {
    const [nextInspection, setNextInspection] = useState(parseDate(vehicle?.nextTechnicalInspection ?? getDateNow()));
    const [isOperational, setIsOperational] = useState<boolean>(vehicle?.operational ?? false);

    const onFormSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.currentTarget;
        const type: VehicleType = form.type.value;
        const engineNumber: string = form.engineNumber.value;
        const engineCapacity: string = form.engineCapacity.value;
        const brand: string = form.brand.value;
        const maximumCapacity: string = form.maximumCapacity.value;
        const operational: boolean = isOperational;

        const nextTechnicalInspection = dateParse(nextInspection.day, nextInspection.month, nextInspection.year);

        const vehicleValue: PutVehicle = {
            type,
            engineNumber,
            engineCapacity: parseInt(engineCapacity),
            operational,
            nextTechnicalInspection,
            brand,
            maximumCapacity: parseInt(maximumCapacity),
        };

        if (vehicle?.id) {
            updateVehicle(vehicle.id, vehicleValue).then((vehicle) => {
                onVehicleEdit(vehicle);
            });
        } else {
            createVehicle(vehicleValue).then((vehicle) => {
                onVehicleAdd(vehicle);
            });
        }

        onClose();
    };

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <div className="employee-modal-wrapper">
                <Form
                    autoComplete="off"
                    isReadOnly={readOnly}
                    onSubmit={onFormSubmit}
                    validationBehavior="native"
                    position={"relative"}
                >
                    <Picker
                        isDisabled={readOnly}
                        name="type"
                        label="Vehicle type"
                        isRequired
                        defaultSelectedKey={`${vehicle?.type}`}
                    >
                        {["TRUCK", "PASSENGER_CAR"].map((type) => (
                            <Item key={type}>{type}</Item>
                        ))}
                    </Picker>
                    <TextField label="Brand" name="brand" defaultValue={vehicle?.brand} isRequired />
                    <TextField
                        label="Engine Number"
                        name="engineNumber"
                        defaultValue={vehicle?.engineNumber}
                        isRequired
                    />
                    <NumberField
                        label="Engine Capacity"
                        name="engineCapacity"
                        defaultValue={vehicle?.engineCapacity}
                        minValue={1}
                        isRequired
                    />
                    <NumberField
                        label="Maximum load"
                        name="maximumCapacity"
                        defaultValue={vehicle?.maximumCapacity}
                        minValue={1}
                        isRequired
                    />
                    <Checkbox name="operational" isSelected={isOperational} onChange={setIsOperational}>
                        Is Operational
                    </Checkbox>
                    <DatePicker
                        label="Next Technical Inspection"
                        value={nextInspection}
                        onChange={setNextInspection}
                        isRequired
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
