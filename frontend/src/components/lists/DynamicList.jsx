import Button from "../forms/Button";
import { MdDelete, MdEdit } from "react-icons/md";
import { Droppable, Draggable } from "react-beautiful-dnd";

const DynamicList = ({
    title,
    guestList,
    handleOpenDeleteModal,
    handleOpenEditModal,
    listId,
}) => {
    return (
        <div className="guest-list">
            <h2>{title}</h2>
            <Droppable droppableId={listId}>
                {(provided) => (
                    <div
                        className="droppable-list"
                        ref={provided.innerRef}
                        {...provided.droppableProps}
                    >
                        {guestList.map((guest, index) => (
                            <Draggable
                                key={guest.id}
                                draggableId={guest.id.toString()}
                                index={index}
                            >
                                {(provided) => (
                                    <div
                                        className="list-item space-between"
                                        ref={provided.innerRef}
                                        {...provided.draggableProps}
                                        {...provided.dragHandleProps}
                                    >
                                        <p className="list-item-text">
                                            {guest.name} {guest.surname}
                                        </p>
                                        <div className="icons-container">
                                            <Button
                                                onClick={() =>
                                                    handleOpenEditModal(guest)
                                                }
                                            >
                                                <MdEdit className="icon" />
                                            </Button>
                                            <Button
                                                onClick={() =>
                                                    handleOpenDeleteModal(guest)
                                                }
                                            >
                                                <MdDelete className="icon" />
                                            </Button>
                                        </div>
                                    </div>
                                )}
                            </Draggable>
                        ))}
                        {provided.placeholder}
                    </div>
                )}
            </Droppable>
        </div>
    );
};

export default DynamicList;
