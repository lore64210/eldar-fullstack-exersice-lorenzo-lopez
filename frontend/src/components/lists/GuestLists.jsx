import { useEffect, useState } from "react";
import { STATUS } from "../../constants/constants";
import StaticList from "./StaticList";
import DynamicList from "./DynamicList";
import { DragDropContext } from "react-beautiful-dnd";

const reorder = (list, startIndex, endIndex) => {
    const result = Array.from(list);
    const [removed] = result.splice(startIndex, 1);
    result.splice(endIndex, 0, removed);

    return result;
};

const GuestLists = ({
    guests,
    setGuests,
    handleOpenEditModal,
    handleOpenDeleteModal,
}) => {
    const [lists, setLists] = useState({
        [STATUS.possible]: guests.filter(
            (guest) => guest.status === STATUS.possible
        ),
        [STATUS.confirmed]: guests.filter(
            (guest) => guest.status === STATUS.confirmed
        ),
        [STATUS.invited]: guests.filter(
            (guest) => guest.status === STATUS.invited
        ),
    });

    useEffect(() => {
        setLists({
            [STATUS.possible]: guests.filter(
                (guest) => guest.status === STATUS.possible
            ),
            [STATUS.confirmed]: guests.filter(
                (guest) => guest.status === STATUS.confirmed
            ),
            [STATUS.invited]: guests.filter(
                (guest) => guest.status === STATUS.invited
            ),
        });
    }, [guests]);

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
            {!!lists?.invitedGuests?.length && (
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
