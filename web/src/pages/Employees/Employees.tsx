import { useEffect, useState } from "react";
import { Employee } from "../../types/Employee";
import { deleteEmployee, getEmployees } from "../../api/Employees";
import EmployeesList from "./EmployeesList";
import { EmployeeAddModal } from "../../components/Modal/EmployeeModal/EmployeeAddModal";
import { EmployeeEditModal } from "../../components/Modal/EmployeeModal/EmployeeEditModal";

export default function Employees() {
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedEmployee, setSelectedEmployee] = useState<Employee>();

    const onEmployeeEdit = (employee: Employee) => {
        setSelectedEmployee(employee);
        setIsModalOpen(true);
    };

    const onEmployeeAdd = () => {
        setIsModalOpen(true);
    };

    const onEmployeeDelete = (id: number) => {
        deleteEmployee(id);
        setEmployees(employees.filter((employee) => employee.id !== id));
    };

    useEffect(() => {
        getEmployees().then((employees) => {
            setEmployees(employees);
        });
    }, []);

    return (
        <>
            {isModalOpen && selectedEmployee && (
                <EmployeeEditModal
                    isOpen={isModalOpen}
                    readOnly={false}
                    onClose={() => {
                        setIsModalOpen(false);
                        setSelectedEmployee(undefined);
                    }}
                    onEmployeeEdit={(employeeEdited) => {
                        const filteredEmployees = employees.filter((employee) => employee.id != employeeEdited.id);
                        setEmployees([...filteredEmployees, employeeEdited]);
                    }}
                    employee={selectedEmployee}
                />
            )}
            {isModalOpen && !selectedEmployee && (
                <EmployeeAddModal
                    isOpen={isModalOpen}
                    onClose={() => {
                        setIsModalOpen(false);
                    }}
                    onEmployeeAdd={(employee) => {
                        setEmployees([...employees, employee]);
                    }}
                />
            )}
            <EmployeesList
                employees={employees}
                onEmployeeDelete={onEmployeeDelete}
                onEmployeeAdd={onEmployeeAdd}
                onEmployeeEdit={onEmployeeEdit}
            />
        </>
    );
}
