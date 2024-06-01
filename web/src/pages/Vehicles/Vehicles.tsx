import { useEffect, useState } from "react";
import { Vehicle } from "../../types/Vehicle";
import { deleteVehicle, getVehicles } from "../../api/Vehicles";
import { VehiclesModal } from "../../components/Modal/VehiclesModal/VehiclesModal";
import VehiclesList from "./VehiclesList";

export default function Vehicles() {
    const [vehicles, setVehicles] = useState<Vehicle[]>([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedVehicle, setSelectedVehicle] = useState<Vehicle>();
    const [isViewingVehicle, setIsViewingVehicle] = useState(false);

    useEffect(() => {
        getVehicles().then((vehicles) => {
            setVehicles(vehicles);
        });
    }, []);

    const onVehicleEdit = (vehicle: Vehicle) => {
        setSelectedVehicle(vehicle);
        setIsModalOpen(true);
    };

    const onVehicleAdd = () => {
        setIsModalOpen(true);
    };

    const onVehicleDelete = (id: number) => {
        deleteVehicle(id);
        setVehicles(vehicles.filter((vehicle) => vehicle.id !== id));
    };

    const onVehicleView = (vehicle: Vehicle) => {
        setIsViewingVehicle(true);
        setSelectedVehicle(vehicle);
        setIsModalOpen(true);
    };

    return (
        <>
            {isModalOpen && (
                <VehiclesModal
                    vehicle={selectedVehicle}
                    isOpen={isModalOpen}
                    readOnly={isViewingVehicle}
                    onClose={() => {
                        setIsModalOpen(false);
                        setIsViewingVehicle(false);
                        setSelectedVehicle(undefined);
                    }}
                    onVehicleAdd={(vehicle) => {
                        setVehicles([...vehicles, vehicle]);
                    }}
                    onVehicleEdit={(vehicle) => {
                        const filteredVehicles = vehicles.filter((vehicle) => vehicle.id != selectedVehicle?.id);
                        setVehicles([...filteredVehicles, vehicle]);
                    }}
                />
            )}
            <VehiclesList
                vehicles={vehicles}
                onVehicleAdd={onVehicleAdd}
                onVehicleDelete={onVehicleDelete}
                onVehicleEdit={onVehicleEdit}
                onVehicleView={onVehicleView}
            />
        </>
    );
}
