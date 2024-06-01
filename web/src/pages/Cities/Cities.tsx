import { useEffect, useState } from "react";
import { City } from "../../types/Cities";
import { deleteCity, getCities } from "../../api/Cities";
import CitiesList from "./CitiesList";
import CityAddModal from "../../components/Modal/CityModal/CityAddModal";

export default function Cities() {
    const [cities, setCities] = useState<City[]>([]);
    const [isAddModalOpen, setIsEditModalOpen] = useState(false);

    useEffect(() => {
        getCities().then((cities) => {
            setCities(cities);
        });
    }, []);

    const onCityDelete = (id: number) => {
        deleteCity(id).then(() => {
            setCities(cities.filter((city) => city.id !== id));
        });
    };

    return (
        <>
            {isAddModalOpen && (
                <CityAddModal
                    isOpen={isAddModalOpen}
                    onCityAdd={(city) => {
                        setCities([...cities, city]);
                    }}
                    onClose={() => {
                        setIsEditModalOpen(false);
                    }}
                />
            )}
            <CitiesList
                cities={cities}
                onCityAdd={() => {
                    setIsEditModalOpen(true);
                }}
                onCityDelete={onCityDelete}
            />
        </>
    );
}
