import { useContext } from "react";
import { AuthorizationContext } from "../../components/Authorization/Authorization";
import { Flex, Heading, View } from "@adobe/react-spectrum";
import { CoordinatorDashboard } from "./CoordinatorDashboard";
import { AdminDashboard } from "./AdminDashboard";
import { MechanicDashboard } from "./MechanicDashboard";
import { DriverDashboard } from "./DriverDashboard";

export default function Dashboard() {
    const { position } = useContext(AuthorizationContext);

    return (
        <View padding={"80px"}>
            <Flex justifyContent="center" alignItems="center" direction="column" gap={"size-300"}>
                <Heading level={1}>{`Welcome ${position}!`}</Heading>
                {position === "coordinator" && <CoordinatorDashboard />}
                {position === "admin" && <AdminDashboard />}
                {position === "mechanic" && <MechanicDashboard />}
                {position === "driver" && <DriverDashboard />}
            </Flex>
        </View>
    );
}
