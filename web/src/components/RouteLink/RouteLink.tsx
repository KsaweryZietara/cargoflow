import { Button, Flex, View } from "@adobe/react-spectrum";
import { useNavigate } from "react-router-dom";

interface RouteLinkProps {
    to: string;
    children: JSX.Element;
}

export default function RouteLink({ to, children }: RouteLinkProps) {
    const navigate = useNavigate();

    return (
        <Button
            onPress={() => {
                navigate(`/${to}`);
            }}
            variant="overBackground"
            type="button"
        >
            <View padding={"size-300"} width="size-3600" maxWidth={"size-3000"}>
                <Flex alignItems="center" gap={"size-200"}>
                    {children}
                </Flex>
            </View>
        </Button>
    );
}
