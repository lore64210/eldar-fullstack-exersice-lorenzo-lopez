export const emptyGuest = {
    id: null,
    name: "",
    surname: "",
    phoneNumber: "",
    email: "",
};

export const REQUIRED_FIELD_MESSAGE = "El campo es obligatorio";
export const INVALID_NAME_MESSAGE = "El nombre es invalido";
export const INVALID_SURNAME_MESSAGE = "El apellido es invalido";
export const INVALID_EMAIL_MESSAGE = "El email es invalido";
export const INVALID_PHONE_NUMBER_MESSAGE = "El numero de telefono es invalido";

export const NAME_PATTERN = /^[A-Za-z]+$/;
export const SURNAME_PATTERN = /^[A-Za-z]+$/;
export const EMAIL_PATTERN =
    /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$\b/;
export const PHONE_NUMBER_PATTERN = /\d+\b/;
