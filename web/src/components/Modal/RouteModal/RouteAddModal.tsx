import { Button, ButtonGroup, Flex, Form, Item, NumberField, Picker, TextField } from "@adobe/react-spectrum";
import { Route } from "../../../types/Routes";
import Modal from "../Modal";
import { City } from "../../../types/Cities";
import { useEffect, useState } from "react";
import { getCities } from "../../../api/Cities";
import { addRoute } from "../../../api/Routes";

interface RouteAddModalProps {
    isOpen: boolean;
    onClose: () => void;
    onRouteAdd: (route: Route) => void;
}

export default function RouteAddModal({ isOpen, onClose, onRouteAdd }: RouteAddModalProps) {
    const [cities, setCities] = useState<City[]>([]);

    useEffect(() => {
        getCities().then((cities) => setCities(cities));
    }, []);

    const onFormSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.currentTarget;

        const departureCityId: number = form.departureCity.value;
        const arrivalCityId: number = form.arrivalCity.value;
        const distance: number = form.distance.value;

        addRoute({
            arrivalCityId,
            departureCityId,
            distance
        }).then((route) => {
            onRouteAdd(route)
        })

        onClose();
    };
    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <div className="employee-modal-wrapper">
                <Form autoComplete="off" onSubmit={onFormSubmit} validationBehavior="native" position={"relative"}>
                    <Picker name="departureCity" label="Departure City" isRequired>
                        {cities.map((city) => (
                            <Item key={city.id}>{city.name + ' - ' + city.address}</Item>
                        ))}
                    </Picker>
                    <Picker name="arrivalCity" label="Arrival City" isRequired>
                        {cities.map((city) => (
                            <Item key={city.id}>{city.name + ' - ' + city.address}</Item>
                        ))}
                    </Picker>
                    <NumberField name="distance" label="Distance" defaultValue={0} minValue={0} isRequired/>

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
