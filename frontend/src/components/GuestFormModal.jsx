import { useCallback, forwardRef, useState } from "react";
import { createGuest, updateGuest } from "../service/guestService";
import Modal from "./Modal";
import GuestForm from "./GuestForm";

const GuestFormModal = forwardRef(({ guest, setGuest }, dialogRef) => {
    const [isLoading, setIsLoading] = useState(false);

    const handleSaveGuest = useCallback(async () => {
        setIsLoading(true);
        if (guest?.id) {
            await updateGuest(guest);
        } else {
            await createGuest(guest);
        }
        setIsLoading(false);
    }, [guest]);

    const handleCloseModal = useCallback(() => {
        dialogRef.current.close();
    }, []);

    return (
        <Modal
            ref={dialogRef}
            onClose={handleCloseModal}
            isLoading={isLoading}
            submitButtonText={guest?.id ? "Editar" : "Crear"}
            title={guest?.id ? "Editar invitado" : "Crear nuevo invitado"}
            className=""
            contentClassName=""
        >
            <GuestForm
                guest={guest}
                setGuest={setGuest}
                onSubmit={handleSaveGuest}
            />
        </Modal>
    );
});

export default GuestFormModal;
