import { useRef, useEffect, useCallback, useState } from "react";
import { findAllGuests } from "./service/guestService";
import { emptyGuest } from "./constants/constants";
import GuestFormModal from "./components/GuestFormModal";
import Button from "./components/forms/Button";
import "./styles/App.scss";

function App() {
    const [selectedGuest, setSelectedGuest] = useState(null);
    const [guests, setGuests] = useState([]);
    const dialogRef = useRef();

    useEffect(() => {
        const getGuestsAsync = async () => {
            const newGuests = await findAllGuests();
            setGuests(newGuests);
        };
        getGuestsAsync();
    }, []);

    const handleOpenCreateGuestModal = useCallback(() => {
        setSelectedGuest(emptyGuest);
        handleOpenGuestModal();
    }, []);

    const handleOpenGuestModal = () => {
        dialogRef.current.showModal();
    };

    return (
        <>
            <h1>Mi lista de inviitados</h1>
            <Button onClick={handleOpenCreateGuestModal}>
                Agregar Invitado
            </Button>
            <GuestFormModal
                ref={dialogRef}
                guest={selectedGuest}
                setGuest={setSelectedGuest}
            />
        </>
    );
}

export default App;
