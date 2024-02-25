import { useCallback, forwardRef, useState } from "react";
import Modal from "./Modal";
import GuestForm from "./GuestForm";

const GuestFormModal = forwardRef(({ guest, setGuest }, dialogRef) => {
    const [isLoading, setIsLoading] = useState(false);

    const handleCloseModal = useCallback(() => {
        dialogRef.current.close();
    }, []);

    return (
        <Modal
            ref={dialogRef}
            onClose={handleCloseModal}
            isLoading={isLoading}
            title={guest?.id ? "Editar invitado" : "Crear nuevo invitado"}
        >
            <GuestForm
                guest={guest}
                setGuest={setGuest}
                setIsLoading={setIsLoading}
            />
        </Modal>
    );
});

export default GuestFormModal;
