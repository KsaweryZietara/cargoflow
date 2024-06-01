import axios, { AxiosInstance } from "axios";

export const api: AxiosInstance = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
        "Cache-Control": "no-cache",
        Pragma: "no-cache",
        Expires: "0",
    },
});

export const setApiInstanceToken = (token: string): void => {
    api.defaults.headers["Authorization"] = `Basic ${token}`;
};

export const clearApiInstanceToken = (): void => {
    api.defaults.headers["Authorization"] = null;
};