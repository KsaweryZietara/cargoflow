import { createBrowserRouter, RouterProvider } from "react-router-dom";
import routes from "./routing/routes";
import { defaultTheme, Flex, Provider } from "@adobe/react-spectrum";
import Authorization from "./components/Authorization/Authorization";
import Navigation from "./components/Navigation/Navigation";

const router = createBrowserRouter(routes);

export default function BasicExample() {
    return (
        <Provider theme={defaultTheme}>
            <Authorization>
                <Flex height={"100vh"} direction={"column"}>
                    <RouterProvider router={router} />
                </Flex>
            </Authorization>
        </Provider>
    );
}
