import { Flex, View } from "@adobe/react-spectrum";
import { DriverLicence } from "../../types/Employee";
import { useEffect, useState } from "react";
import "./LicensePicker.scss";
import { addLicense, deleteLicense, getLicenses } from "../../api/Licenses";

export interface LicensePickerProps {
    licenses: DriverLicence[];
    setLicenses: (licenses: DriverLicence[]) => void;
    isViewOnly?: boolean;
}

export default function LicensePicker({ licenses, setLicenses, isViewOnly = false }: LicensePickerProps) {
    const onRemoveLicense = (id: number) => setLicenses(licenses.filter((license) => license.id !== id));

    const [isPickerOpened, setIsPickerOpened] = useState(false);

    return (
        <>
            {isPickerOpened && (
                <Picker
                    closePicker={() => {
                        setIsPickerOpened(false);
                    }}
                    onLicensePick={(license) => {
                        setLicenses([...licenses, license]);
                    }}
                    onLicenseRemove={(license) => {
                        onRemoveLicense(license.id);
                    }}
                    alreadyPickedLicenses={licenses}
                />
            )}
            <span>Driver licenses:</span>
            <View borderWidth={"thin"} padding={"10px"} >
                <Flex width={"100%"} gap={"10px"}>
                    <>
                        {licenses.map((license: DriverLicence) => (
                            <button
                                onClick={() => {
                                    onRemoveLicense(license.id);
                                }}
                                className="license-tile"
                                type="button"
                                disabled={isViewOnly}
                            >
                                {license.name}
                            </button>
                        ))}
                        {!isViewOnly && (
                            <button
                                onClick={() => {
                                    setIsPickerOpened(true);
                                }}
                                type="button"
                                className="license-tile license-new"
                            >
                                +
                            </button>
                        )}
                    </>
                </Flex>
            </View>
        </>
    );
}

interface PickerProps {
    closePicker: () => void;
    onLicensePick: (license: DriverLicence) => void;
    onLicenseRemove: (license: DriverLicence) => void;
    alreadyPickedLicenses: DriverLicence[];
}

function Picker({ closePicker, onLicensePick, onLicenseRemove, alreadyPickedLicenses }: PickerProps) {
    const [availableLicenses, setAvailableLicenses] = useState<DriverLicence[]>([]);
    const [newLicenseName, setNewLicenseName] = useState("");

    useEffect(() => {
        getLicenses().then((licenses) => {
            const filteredLicenses = licenses.filter(
                (license) => !alreadyPickedLicenses.some((pickedLicense) => pickedLicense.id === license.id)
            );
            setAvailableLicenses(filteredLicenses);
        });
    }, [alreadyPickedLicenses]);

    const onAddNewLicense = () => {
        if (newLicenseName) {
            addLicense(newLicenseName).then((newLicense) => {
                setAvailableLicenses([...availableLicenses, newLicense]);
            });
            setNewLicenseName("");
        }
    };

    const onDeleteLicense = (license: DriverLicence) => {
        deleteLicense(license.id).then(() => {
            onLicenseRemove(license);
        });
    };

    return (
        <div className="picker">
            <div className="picker-header">
                <Flex justifyContent={"end"}>
                    <button onClick={() => closePicker()} className="modal-close-btn" type="button">
                        X
                    </button>
                </Flex>
            </div>
            <div className="picker-items">
                {availableLicenses.map((license) => (
                    <p className="picker-item">
                        <b className="license-name">{license.name}</b>
                        <span className="btn-wrapper">
                            <button
                                className="license-pick"
                                type="button"
                                onClick={() => {
                                    onLicensePick(license);
                                }}
                            >
                                Pick
                            </button>
                            <button
                                type="button"
                                onClick={() => {
                                    onDeleteLicense(license);
                                }}
                            >
                                Delete
                            </button>
                        </span>
                    </p>
                ))}
            </div>
            <div className="picker-input">
                <span>
                    <input
                        type="text"
                        value={newLicenseName}
                        onChange={(e) => {
                            setNewLicenseName(e.target.value);
                        }}
                        onSubmit={() => {}}
                    />
                    <button onClick={onAddNewLicense} type="button">
                        Add
                    </button>
                </span>
            </div>
        </div>
    );
}
