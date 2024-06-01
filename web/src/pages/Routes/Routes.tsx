import { useEffect, useState } from "react";
import { Route } from "../../types/Routes";
import { deleteRoute, getRoutes } from "../../api/Routes";
import RouteAddModal from "../../components/Modal/RouteModal/RouteAddModal";
import RoutesList from "./RoutesList";

export default function Routes() {
    const [routes, setRoutes] = useState<Route[]>([]);
    const [isAddModalOpen, setIsEditModalOpen] = useState(false);

    useEffect(() => {
        getRoutes().then((routes) => {
            setRoutes(routes);
        });
    }, []);

    const onRouteDelete = (id: number) => {
        deleteRoute(id).then(() => {
            setRoutes(routes.filter((route) => route.id !== id));
        });
    };

    return (
        <>
            {isAddModalOpen && (
                <RouteAddModal
                    isOpen={isAddModalOpen}
                    onRouteAdd={(route) => {
                        setRoutes([...routes, route]);
                    }}
                    onClose={() => {
                        setIsEditModalOpen(false);
                    }}
                />
            )}
            {
                <RoutesList
                    routes={routes}
                    onRouteAdd={() => {
                        setIsEditModalOpen(true);
                    }}
                    onRouteDelete={(id: number) => {
                        onRouteDelete(id);
                    }}
                />
            }
        </>
    );
}
