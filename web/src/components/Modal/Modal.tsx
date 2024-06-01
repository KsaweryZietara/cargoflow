import { FC, useState } from "react";
import ReactDOM from "react-dom";
import "./Modal.scss";

interface ModalProps {
    isOpen: boolean;
    onClose: () => void;
    children: JSX.Element;
    rootId?: string;
    wide?: boolean;
}

const Modal: FC<ModalProps> = ({ isOpen, onClose, children, wide, rootId = "modal-root" }) => {
    const [isModalOpen, setIsModalOpen] = useState<boolean>(isOpen);

    const closeModal = (): void => {
        setIsModalOpen(false);
        onClose();
    };

    if (!isModalOpen) return null;

    return ReactDOM.createPortal(
        <div className="modal-overlay">
            <div
                className="modal"
                style={{ position: "relative", width: "100%", maxWidth: wide ? "1400px" : undefined }}
            >
                <div className="modal-header">
                    <button className="modal-close-btn" onClick={closeModal}>
                        X
                    </button>
                </div>
                <div className="modal-content">{children}</div>
            </div>
        </div>,
        document.getElementById(rootId)!
    );
};

export default Modal;
