const findAllGuests = async () => {
    return await fetch("/api/guest")
        .then((response) => response.json())
        .catch((error) => console.error("Solicitud fallida", error));
};

const findGuestById = async (id) => {
    return await fetch(`/api/guest/${id}`)
        .then((response) => response.json())
        .catch((error) => console.error("Solicitud fallida", error));
};

const createGuest = async (newGuest) => {
    return await fetch("/api/guest", {
        method: "POST",
        body: JSON.stringify(newGuest),
        headers: { "Content-type": "application/json; charset=UTF-8" },
    })
        .then((response) => response.json())
        .catch((error) => console.error("Solicitud fallida", error));
};

const updateGuest = async (guest) => {
    return await fetch("/api/guest", {
        method: "PUT",
        body: JSON.stringify(guest),
        headers: { "Content-type": "application/json; charset=UTF-8" },
    })
        .then((response) => response.json())
        .catch((error) => console.error("Solicitud fallida", error));
};

const deleteGuest = async (id) => {
    await fetch(`/api/guest/${id}`, { method: "DELETE" })
        .then((response) => response.json())
        .catch((error) => console.error("Solicitud fallida", error));
};

const inviteGuests = async (guestIdList) => {
    const body = {
        confirmedGuestIds: guestIdList,
    };
    return await fetch("/api/guest/invite-confirmed", {
        method: "PUT",
        body: JSON.stringify(body),
        headers: { "Content-type": "application/json; charset=UTF-8" },
    })
        .then((response) => response.json())
        .catch((error) => console.error("Solicitud fallida", error));
};

export {
    findAllGuests,
    findGuestById,
    createGuest,
    updateGuest,
    deleteGuest,
    inviteGuests,
};
