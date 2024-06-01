import { Text } from "@adobe/react-spectrum";
import RouteLink from "../../components/RouteLink/RouteLink";
import User from "@spectrum-icons/workflow/User";

export function AdminDashboard() {
    return (
        <>
            <RouteLink to={"employees"}>
                <>
                    <User />
                    <Text>Employees</Text>
                </>
            </RouteLink>
        </>
    );
}
