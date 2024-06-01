import { Text } from "@adobe/react-spectrum";
import RouteLink from "../../components/RouteLink/RouteLink";
import Car from "@spectrum-icons/workflow/Car";

export function MechanicDashboard() {
    return (
        <>
            <RouteLink to={"vehicles"}>
                <>
                    <Car />
                    <Text>Vehicles</Text>
                </>
            </RouteLink>
        </>
    );
}
