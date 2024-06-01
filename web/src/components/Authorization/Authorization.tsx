import { PropsWithChildren, createContext, useCallback, useMemo, useState } from "react";
import { api, clearApiInstanceToken, setApiInstanceToken } from "../../api/Api";
import { Employee, PositionName } from "../../types/Employee";

interface AuthorizationContextModel {
    isLogged: boolean;
    position: PositionName | null;
    login: (username: string, password: string) => Promise<Employee>;
    logout: () => void;
}

export const AuthorizationContext = createContext({} as AuthorizationContextModel);

export default function Authorization({ children }: PropsWithChildren): React.ReactNode {
    const [isLogged, setIsLogged] = useState(false);
    const [position, setPosition] = useState<PositionName | null>(null);

    const login = useCallback(async (username: string, password: string) => {
        const base64Token = btoa(username + ":" + password);
        setApiInstanceToken(base64Token);

        const response = await api.get<Employee>("/employees/current");
        if (response.status === 200) {
            setIsLogged(true);

            const position = response.data.position.name;
            setPosition(position);
        }

        return response.data;
    }, []);

    const logout = useCallback(() => {
        clearApiInstanceToken();
        setIsLogged(false);
        setPosition(null);
    }, []);

    const value = useMemo(() => {
        return { isLogged, position, login, logout };
    }, [isLogged, login, logout, position]);

    return <AuthorizationContext.Provider value={value}>{children}</AuthorizationContext.Provider>;
}
