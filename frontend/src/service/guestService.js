const baseUrl = "http://localhost:8080";

const findAllGuests = async () => {
    try {
        const response = await fetch(baseUrl + "/api/guest");
        return await parseResponse(response);
    } catch (e) {
        if (e.message === "Failed to fetch") {
            throw new Error("No se pudo conectar con el servidor");
        }
    }
};

const createGuest = async (newGuest) => {
    try {
        const response = await fetch(baseUrl + "/api/guest", {
            method: "POST",
            body: JSON.stringify(newGuest),
            headers: { "Content-type": "application/json; charset=UTF-8" },
        });
        return await parseResponse(response);
    } catch (e) {
        if (e.message === "Failed to fetch") {
            throw new Error("No se pudo conectar con el servidor");
        }
        throw e;
    }
};

const updateGuest = async (guest) => {
    try {
        const response = await fetch(baseUrl + "/api/guest", {
            method: "PUT",
            body: JSON.stringify(guest),
            headers: { "Content-type": "application/json; charset=UTF-8" },
        });
        return await parseResponse(response);
    } catch (e) {
        if (e.message === "Failed to fetch") {
            throw new Error("No se pudo conectar con el servidor");
        }
        throw e;
    }
};

const deleteGuest = async (id) => {
    try {
        await fetch(baseUrl + `/api/guest/${id}`, { method: "DELETE" })
            .then((response) => response.json())
            .catch((error) => console.error("Solicitud fallida", error));
        return await parseResponse(response);
    } catch (e) {
        if (e.message === "Failed to fetch") {
            throw new Error("No se pudo conectar con el servidor");
        }
        throw e;
    }
};

const inviteGuests = async (guestIdList) => {
    try {
        const body = {
            confirmedGuestIds: guestIdList,
        };
        const response = await fetch(baseUrl + "/api/guest/invite-confirmed", {
            method: "PUT",
            body: JSON.stringify(body),
            headers: { "Content-type": "application/json; charset=UTF-8" },
        });
        return await parseResponse(response);
    } catch (e) {
        if (e.message === "Failed to fetch") {
            throw new Error("No se pudo conectar con el servidor");
        }
        throw e;
    }
};

const parseResponse = async (response) => {
    if (response.status === 200) {
        return await response.json();
    } else {
        const { error } = await response.json();
        throw new Error(error);
    }
};

export { findAllGuests, createGuest, updateGuest, deleteGuest, inviteGuests };
