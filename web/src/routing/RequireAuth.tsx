import { useContext } from "react";
import { AuthorizationContext } from "../components/Authorization/Authorization";
import { Navigate } from "react-router-dom";

export default function RequireAuth({ children }: { children: JSX.Element }) {
    const { isLogged } = useContext(AuthorizationContext);

    if (!isLogged) {
        return <Navigate to="/" />;
    }

    return children;
}
