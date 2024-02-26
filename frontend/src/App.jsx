import { useRef, useEffect, useCallback, useState } from "react";
import { findAllGuests, inviteGuests } from "./service/guestService";
import { STATUS, emptyGuest } from "./constants/constants";
import GuestFormModal from "./components/modals/GuestFormModal";
import Button from "./components/forms/Button";
import "./styles/App.scss";
import GuestLists from "./components/lists/GuestLists";
import Loading from "./components/Loading";
import DeleteGuestModal from "./components/modals/DeleteGuestModal";

function App() {
    const [isLoading, setIsLoading] = useState(true);
    const [selectedGuest, setSelectedGuest] = useState(null);
    const [selectedGuestToDelete, setSelectedGuestToDelete] = useState(null);
    const [genericError, setGenericError] = useState(null);
    const [inviteError, setInviteError] = useState(null);
    const [guests, setGuests] = useState([]);

    const dialogRef = useRef();
    const deleteDialogRef = useRef();

    useEffect(() => {
        const getGuestsAsync = async () => {
            try {
                const newGuests = await findAllGuests();
                setGuests(newGuests);
            } catch (e) {
                setGenericError(e.message);
            }
            setIsLoading(false);
        };
        getGuestsAsync();
    }, []);

    const handleOpenCreateGuestModal = useCallback(() => {
        setSelectedGuest(emptyGuest);
        handleOpenGuestModal();
    }, []);

    const handleOpenUpdateGuestModal = useCallback((guest) => {
        setSelectedGuest(guest);
        handleOpenGuestModal();
    });

    const handleOpenDeleteGuestModal = useCallback((guest) => {
        setSelectedGuestToDelete(guest);
        handleOpenDeleteModal();
    });

    const handleOpenGuestModal = () => {
        dialogRef.current.showModal();
    };

    const handleOpenDeleteModal = () => {
        deleteDialogRef.current.showModal();
    };

    const handleInviteConfirmedGuests = async () => {
        try {
            setIsLoading(true);
            const confirmedGuestIds = guests
                .filter((guest) => guest.status === STATUS.confirmed)
                .map((guest) => guest.id);
            await inviteGuests(confirmedGuestIds);
            setGuests(
                guests.map((g) =>
                    confirmedGuestIds.includes(g.id)
                        ? { ...g, status: STATUS.invited }
                        : g
                )
            );
        } catch (e) {
            setInviteError(e.message);
        }
        setIsLoading(false);
    };

    if (genericError) {
        return <h1 className="centered">{genericError}</h1>;
    }

    return (
        <>
            {isLoading && <Loading />}
            <h1 className="centered title">Mi lista de invitados :D</h1>
            <Button
                className="centered create-btn"
                onClick={handleOpenCreateGuestModal}
            >
                Agregar Invitado
            </Button>
            <Button
                className="centered invite-btn"
                onClick={handleInviteConfirmedGuests}
            >
                Invitar a los confirmados
            </Button>
            {inviteError && <p className="error centered">{inviteError}</p>}
            <GuestLists
                guests={guests}
                setGuests={setGuests}
                handleOpenEditModal={handleOpenUpdateGuestModal}
                handleOpenDeleteModal={handleOpenDeleteGuestModal}
            />
            <GuestFormModal
                ref={dialogRef}
                guest={selectedGuest}
                setGuest={setSelectedGuest}
                guests={guests}
                setGuests={setGuests}
            />
            <DeleteGuestModal
                ref={deleteDialogRef}
                guest={selectedGuestToDelete}
                setGuest={setSelectedGuestToDelete}
                guests={guests}
                setGuests={setGuests}
            />
        </>
    );
}

export default App;
