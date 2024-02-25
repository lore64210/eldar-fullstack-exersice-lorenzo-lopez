import { useMemo } from "react";
import { STATUS } from "../constants/constants";
import Button from "./forms/Button";
import { MdDelete, MdEdit } from "react-icons/md";

const GuestLists = ({ guests, handleOpenEditModal, handleOpenDeleteModal }) => {
    const possibleGuests = useMemo(() => {
        return guests.filter((guest) => guest.status === STATUS.possible);
    }, [guests]);

    const confirmedGuests = useMemo(() => {
        return guests.filter((guest) => guest.status === STATUS.confirmed);
    }, [guests]);

    const invitedGuests = useMemo(() => {
        return guests.filter((guest) => guest.status === STATUS.invited);
    }, [guests]);

    return (
        <>
            <div className="space-around lists-container">
                <GuestList
                    title="Posibles invitados"
                    guestList={possibleGuests}
                    handleOpenDeleteModal={handleOpenDeleteModal}
                    handleOpenEditModal={handleOpenEditModal}
                    showButtons
                />
                <GuestList
                    title="Confirmados"
                    guestList={confirmedGuests}
                    handleOpenDeleteModal={handleOpenDeleteModal}
                    handleOpenEditModal={handleOpenEditModal}
                    showButtons
                />
            </div>
            {!!invitedGuests?.length && (
                <div className="centered">
                    <GuestList
                        title="Ya invitados!"
                        guestList={invitedGuests}
                        handleOpenDeleteModal={handleOpenDeleteModal}
                        handleOpenEditModal={handleOpenEditModal}
                    />
                </div>
            )}
        </>
    );
};

const GuestList = ({
    title,
    guestList,
    handleOpenDeleteModal,
    handleOpenEditModal,
    showButtons = false,
}) => {
    return (
        <div className="guest-list">
            <h2>{title}</h2>
            {guestList.map((guest) => (
                <div key={guest.id} className="list-item space-between">
                    <p className="list-item-text">
                        {guest.name} {guest.surname}
                    </p>
                    {showButtons && (
                        <div className="icons-container">
                            <Button onClick={() => handleOpenEditModal(guest)}>
                                <MdEdit className="icon" />
                            </Button>
                            <Button
                                onClick={() => handleOpenDeleteModal(guest)}
                            >
                                <MdDelete className="icon" />
                            </Button>
                        </div>
                    )}
                </div>
            ))}
        </div>
    );
};

export default GuestLists;
