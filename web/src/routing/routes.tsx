import { Route, createRoutesFromElements } from "react-router-dom";
import LoginPage from "../pages/Login/LoginPage";
import Dashboard from "../pages/Dashboard/Dashboard";
import RequireAuth from "./RequireAuth";
import Employees from "../pages/Employees/Employees";
import Layout from "../components/Layout/Layout";
import Cities from "../pages/Cities/Cities";
import Routes from "../pages/Routes/Routes";
import Vehicles from "../pages/Vehicles/Vehicles";
import Jobs from "../pages/Jobs/Jobs";

const routes = createRoutesFromElements(
    <Route path="/" element={<Layout />}>
        <Route
            path="dashboard"
            element={
                <RequireAuth>
                    <Dashboard />
                </RequireAuth>
            }
        />
        <Route
            path="employees"
            element={
                <RequireAuth>
                    <Employees />
                </RequireAuth>
            }
        />
        <Route
            path="cities"
            element={
                <RequireAuth>
                    <Cities />
                </RequireAuth>
            }
        />
        <Route
            path="routes"
            element={
                <RequireAuth>
                    <Routes />
                </RequireAuth>
            }
        />
        <Route
            path="vehicles"
            element={
                <RequireAuth>
                    <Vehicles />
                </RequireAuth>
            }
        />
        <Route
            path="jobs"
            element={
                <RequireAuth>
                    <Jobs />
                </RequireAuth>
            }
        />
        <Route path="/" element={<LoginPage />} />
    </Route>
);

export default routes;
