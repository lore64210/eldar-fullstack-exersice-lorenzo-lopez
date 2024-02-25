const baseUrl = "http://localhost:8080";

const findAllGuests = async () => {
    const response = await fetch(baseUrl + "/api/guest");
    return await parseResponse(response);
};

const findGuestById = async (id) => {
    const response = await fetch(baseUrl + `/api/guest/${id}`);
    return await parseResponse(response);
};

const createGuest = async (newGuest) => {
    const response = await fetch(baseUrl + "/api/guest", {
        method: "POST",
        body: JSON.stringify(newGuest),
        headers: { "Content-type": "application/json; charset=UTF-8" },
    });
    return await parseResponse(response);
};

const updateGuest = async (guest) => {
    const response = await fetch(baseUrl + "/api/guest", {
        method: "PUT",
        body: JSON.stringify(guest),
        headers: { "Content-type": "application/json; charset=UTF-8" },
    });
    return await parseResponse(response);
};

const deleteGuest = async (id) => {
    await fetch(baseUrl + `/api/guest/${id}`, { method: "DELETE" })
        .then((response) => response.json())
        .catch((error) => console.error("Solicitud fallida", error));
    return await parseResponse(response);
};

const inviteGuests = async (guestIdList) => {
    const body = {
        confirmedGuestIds: guestIdList,
    };
    const response = await fetch(baseUrl + "/api/guest/invite-confirmed", {
        method: "PUT",
        body: JSON.stringify(body),
        headers: { "Content-type": "application/json; charset=UTF-8" },
    });
    return await parseResponse(response);
};

const parseResponse = async (response) => {
    if (response.status === 200) {
        return await response.json();
    } else {
        const { error } = await response.json();
        if (error === "Failed to fetch") {
            throw new Error("No se pudo conectar con el servidor");
        }
        throw new Error(error);
    }
};

export {
    findAllGuests,
    findGuestById,
    createGuest,
    updateGuest,
    deleteGuest,
    inviteGuests,
};
