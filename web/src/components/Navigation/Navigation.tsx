import { useContext } from "react";
import { AuthorizationContext } from "../Authorization/Authorization";
import { Button, Flex, View } from "@adobe/react-spectrum";
import { useNavigate } from "react-router-dom";

export default function Navigation() {
    const { isLogged, logout } = useContext(AuthorizationContext);
    const navigate = useNavigate();

    return (
        <Flex height={"size-300"} alignSelf="flex-end">
            <View padding={"size-200"}>
                {isLogged && (
                    <>
                        <Button
                            variant="secondary"
                            type="button"
                            onPress={() => {
                                navigate(-1);
                            }}
                            marginEnd={"size-100"}
                        >
                            go back
                        </Button>
                        <Button variant="secondary" type="button" onPress={logout}>
                            logout
                        </Button>
                    </>
                )}
            </View>
        </Flex>
    );
}
