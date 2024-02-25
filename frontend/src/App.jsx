import { useRef, useEffect, useCallback, useState } from "react";
import { findAllGuests } from "./service/guestService";
import { emptyGuest } from "./constants/constants";
import GuestFormModal from "./components/GuestFormModal";
import Button from "./components/forms/Button";
import "./styles/App.scss";
import GuestLists from "./components/GuestLists";
import Loading from "./components/Loading";

function App() {
    const [isLoading, setIsLoading] = useState(true);
    const [selectedGuest, setSelectedGuest] = useState(null);
    const [genericError, setGenericError] = useState(null);
    const [guests, setGuests] = useState([]);
    const dialogRef = useRef();

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

    const handleOpenGuestModal = () => {
        dialogRef.current.showModal();
    };

    if (genericError) {
        return <h1 className="centered">{genericError}</h1>;
    }

    return (
        <>
            {isLoading && <Loading />}
            <h1>Mi lista de inviitados</h1>
            <Button onClick={handleOpenCreateGuestModal}>
                Agregar Invitado
            </Button>
            <GuestLists guests={guests} />
            <GuestFormModal
                ref={dialogRef}
                guest={selectedGuest}
                setGuest={setSelectedGuest}
            />
        </>
    );
}

export default App;
