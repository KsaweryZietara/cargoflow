import { Text } from "@adobe/react-spectrum";
import Branch1 from "@spectrum-icons/workflow/Branch1";
import Location from "@spectrum-icons/workflow/Location";
import PublishCheck from "@spectrum-icons/workflow/PublishCheck";
import RouteLink from "../../components/RouteLink/RouteLink";

export function CoordinatorDashboard() {
    return (
        <>
            <RouteLink to={"jobs"}>
                <>
                    <PublishCheck />
                    <Text>Jobs</Text>
                </>
            </RouteLink>
            <RouteLink to={"routes"}>
                <>
                    <Branch1 />
                    <Text>Routes</Text>
                </>
            </RouteLink>
            <RouteLink to={"cities"}>
                <>
                    <Location />
                    <Text>Cities</Text>
                </>
            </RouteLink>
        </>
    );
}
