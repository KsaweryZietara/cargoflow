import { addCity } from "../../../api/Cities";
import { City } from "../../../types/Cities";
import Modal from "../Modal";
import { Button, ButtonGroup, Flex, Form, Item, Picker, TextField } from "@adobe/react-spectrum";

interface CityAddModal {
    isOpen: boolean;
    onClose: () => void;
    onCityAdd: (city: City) => void;
}

export default function CityAddModal({ isOpen, onClose, onCityAdd }: CityAddModal) {
    const onFormSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.currentTarget;
        const name: string = form.name.value;
        const address: string = form.address.value;

        addCity({ name, address }).then((city) => {
            onCityAdd(city);
        });

        onClose();
    };

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <div className="employee-modal-wrapper">
                <Form autoComplete="off" onSubmit={onFormSubmit} validationBehavior="native" position={"relative"}>
                    <TextField label="Name" name="name" isRequired />
                    <TextField label="Address" name="address" isRequired />

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
