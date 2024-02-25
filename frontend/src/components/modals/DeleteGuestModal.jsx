import { forwardRef, useCallback, useState } from "react";
import Modal from "./Modal";
import { deleteGuest } from "../../service/guestService";

const DeleteGuestModal = forwardRef(
    ({ guest, setGuest, guests, setGuests }, dialogRef) => {
        const [isLoading, setIsLoading] = useState(false);

        const handleCloseModal = useCallback(() => {
            dialogRef.current.close();
        }, []);

        const handleDeleteGuest = useCallback(async () => {
            setIsLoading(true);
            await deleteGuest(guest.id);
            setGuests(guests.filter((g) => g.id !== guest.id));
            setGuest(null);
            setIsLoading(false);
            handleCloseModal();
        }, [guest]);

        return (
            <Modal
                ref={dialogRef}
                onClose={handleCloseModal}
                isLoading={isLoading}
                title="¿Eliminar invitado?"
                onSubmit={handleDeleteGuest}
                submitButtonText="Eliminar"
                className="delete-guest-modal"
            >
                <p>
                    ¿Desea eliminar al invitado{" "}
                    {!!guest && `${guest.name} ${guest.name}`}?
                </p>
            </Modal>
        );
    }
);

export default DeleteGuestModal;
