import { useMemo } from "react";
import {
    INVALID_EMAIL_MESSAGE,
    INVALID_NAME_MESSAGE,
    INVALID_PHONE_NUMBER_MESSAGE,
    INVALID_SURNAME_MESSAGE,
    REQUIRED_FIELD_MESSAGE,
} from "../constants/constants";

const useFormError = (errors) => {
    const nameError = useMemo(() => {
        if (errors.name) {
            if (errors.name.type === "required") {
                return REQUIRED_FIELD_MESSAGE;
            }
            if (errors.name.type === "pattern") {
                return INVALID_NAME_MESSAGE;
            }
        }
        return "";
    }, [errors.name]);

    const surnameError = useMemo(() => {
        if (errors.surname) {
            if (errors.surname.type === "required") {
                return REQUIRED_FIELD_MESSAGE;
            }
            if (errors.surname.type === "pattern") {
                return INVALID_SURNAME_MESSAGE;
            }
        }
        return "";
    }, [errors.surname]);

    const emailError = useMemo(() => {
        if (errors.email) {
            if (errors.email.type === "required") {
                return REQUIRED_FIELD_MESSAGE;
            }
            if (errors.email.type === "pattern") {
                return INVALID_EMAIL_MESSAGE;
            }
        }
        return "";
    }, [errors.email]);

    const phoneNumberError = useMemo(() => {
        if (errors.phoneNumber) {
            if (errors.phoneNumber.type === "required") {
                return REQUIRED_FIELD_MESSAGE;
            }
            if (errors.phoneNumber.type === "pattern") {
                return INVALID_PHONE_NUMBER_MESSAGE;
            }
        }
        return "";
    }, [errors.phoneNumber]);

    return { nameError, surnameError, emailError, phoneNumberError };
};

export default useFormError;
