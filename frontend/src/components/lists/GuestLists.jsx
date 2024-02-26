import { useEffect, useState } from "react";
import { STATUS } from "../../constants/constants";
import StaticList from "./StaticList";
import DynamicList from "./DynamicList";
import { DragDropContext } from "react-beautiful-dnd";
import { updateGuest } from "../../service/guestService";

const reorder = (list, startIndex, endIndex) => {
    const result = Array.from(list);
    const [removed] = result.splice(startIndex, 1);
    result.splice(endIndex, 0, removed);

    return result;
};

const getOrderedGuestList = (guestList, status, orderKey = "position") => {
    return guestList
        .filter((guest) => guest.status === status)
        .sort((a, b) => (a[orderKey] > b[orderKey] ? 1 : -1));
};

const updateGuestPositions = (guests, newGuest) => {
    // TODO refactor
    // Si hay errores en el ordenamiento, seguro sale de aca
    // Esta replicando la logica del backend, hay que corregir en ambos lados
    const previousGuest = guests.find((g) => g.id === newGuest.id);
    const prevPosition = previousGuest.position;
    const newPosition = newGuest.position;
    return guests.map((g) => {
        if (g.id === newGuest.id) {
            return newGuest;
        } else if (g.status !== newGuest.status) {
            return g;
        } else {
            if (newGuest.status === previousGuest.status) {
                if (newPosition > prevPosition) {
                    if (
                        newPosition >= g.position &&
                        prevPosition < g.position
                    ) {
                        return { ...g, position: g.position - 1 };
                    }
                } else {
                    if (
                        newPosition <= g.position &&
                        prevPosition > g.position
                    ) {
                        return { ...g, position: g.position + 1 };
                    }
                }
            } else if (newPosition <= g.position) {
                return { ...g, position: g.position + 1 };
            }
        }
        return g;
    });
};

const GuestLists = ({
    guests,
    setGuests,
    handleOpenEditModal,
    handleOpenDeleteModal,
}) => {
    const [lists, setLists] = useState({
        [STATUS.possible]: getOrderedGuestList(guests, STATUS.possible),
        [STATUS.confirmed]: getOrderedGuestList(guests, STATUS.confirmed),
        [STATUS.invited]: getOrderedGuestList(guests, STATUS.invited, "name"),
    });

    useEffect(() => {
        setLists({
            [STATUS.possible]: getOrderedGuestList(guests, STATUS.possible),
            [STATUS.confirmed]: getOrderedGuestList(guests, STATUS.confirmed),
            [STATUS.invited]: getOrderedGuestList(
                guests,
                STATUS.invited,
                "name"
            ),
        });
    }, [guests]);

    const handleUpdateGuest = async (guest, status, position) => {
        const newGuest = await updateGuest({
            ...guest,
            status,
            position: position + 1, // por ser el indice del array tengo que sumarle 1
        });
        setGuests(updateGuestPositions(guests, newGuest));
    };

    function onDragEnd(result) {
        const { source, destination } = result;
        if (!destination) {
            return;
        }

        if (destination.droppableId === source.droppableId) {
            if (destination.index === source.index) {
                return;
            }

            const reorderedList = reorder(
                lists[source.droppableId],
                source.index,
                destination.index
            );

            setLists({ ...lists, [source.droppableId]: reorderedList });
        } else {
            const startColumn = [...lists[source.droppableId]];
            const finishColumn = [...lists[destination.droppableId]];
            const [removed] = startColumn.splice(source.index, 1);
            finishColumn.splice(destination.index, 0, removed);

            setLists({
                ...lists,
                [source.droppableId]: startColumn,
                [destination.droppableId]: finishColumn,
            });
        }
        const guest = lists[source.droppableId][source.index];
        handleUpdateGuest(guest, destination.droppableId, destination.index);
    }

    return (
        <>
            <div className="space-around lists-container">
                <DragDropContext onDragEnd={onDragEnd}>
                    <DynamicList
                        title="Posibles invitados"
                        guestList={lists[STATUS.possible]}
                        handleOpenDeleteModal={handleOpenDeleteModal}
                        handleOpenEditModal={handleOpenEditModal}
                        listId={STATUS.possible}
                    />
                    <DynamicList
                        title="Confirmados"
                        guestList={lists[STATUS.confirmed]}
                        handleOpenDeleteModal={handleOpenDeleteModal}
                        handleOpenEditModal={handleOpenEditModal}
                        listId={STATUS.confirmed}
                    />
                </DragDropContext>
            </div>
            {!!lists?.[STATUS.invited]?.length && (
                <div className="centered">
                    <StaticList
                        title="Ya invitados!"
                        guestList={lists[STATUS.invited]}
                    />
                </div>
            )}
        </>
    );
};

export default GuestLists;
