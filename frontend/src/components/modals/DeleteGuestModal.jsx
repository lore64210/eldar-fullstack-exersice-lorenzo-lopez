import { forwardRef, useCallback, useState } from "react";
import Modal from "./Modal";
import { deleteGuest } from "../../service/guestService";
import useStore from "../../store/store";

const DeleteGuestModal = forwardRef(
    ({ guestToDelete, setGuestToDelete }, dialogRef) => {
        const { guests, setGuests } = useStore((state) => state);
        const [isLoading, setIsLoading] = useState(false);

        const handleCloseModal = useCallback(() => {
            dialogRef.current.close();
        }, []);

        const handleDeleteGuest = useCallback(async () => {
            setIsLoading(true);
            await deleteGuest(guestToDelete.id);
            setGuests(guests.filter((g) => g.id !== guestToDelete.id));
            setGuestToDelete(null);
            setIsLoading(false);
            handleCloseModal();
        }, [guestToDelete]);

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
                    {!!guestToDelete &&
                        `${guestToDelete.name} ${guestToDelete.name}`}
                    ?
                </p>
            </Modal>
        );
    }
);

export default DeleteGuestModal;
