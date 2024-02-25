import { useCallback, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import TextInput from "./forms/TextInput";
import Button from "./forms/Button";
import useFormError from "../hooks/useFormError";
import {
    EMAIL_PATTERN,
    NAME_PATTERN,
    PHONE_NUMBER_PATTERN,
    SURNAME_PATTERN,
} from "../constants/constants";
import { createGuest, updateGuest } from "../service/guestService";

const GuestForm = ({
    guest,
    setGuest,
    setIsLoading,
    handleCloseModal,
    guests,
    setGuests,
}) => {
    const [backendError, setBackendError] = useState(null);

    const {
        register,
        handleSubmit,
        formState: { errors },
        reset,
    } = useForm({
        defaultValues: {
            name: guest?.name || "",
            surname: guest?.surname || "",
            email: guest?.email || "",
            phoneNumber: guest?.phoneNumber || "",
        },
    });

    useEffect(() => {
        if (!!guest) {
            reset({
                name: guest?.name || "",
                surname: guest?.surname || "",
                email: guest?.email || "",
                phoneNumber: guest?.phoneNumber || "",
            });
        }
    }, [guest?.id]);

    const { nameError, surnameError, emailError, phoneNumberError } =
        useFormError(errors);

    const handleGuestChange = ({ name, value }) => {
        setGuest({ ...guest, [name]: value });
    };

    const onSubmit = useCallback(async () => {
        try {
            setIsLoading(true);
            if (guest?.id) {
                const updatedGuest = await updateGuest(guest);
                setGuests(
                    guests.map((g) =>
                        g.id === updatedGuest.id ? updatedGuest : g
                    )
                );
            } else {
                const newGuest = await createGuest(guest);
                setGuests([...guests, newGuest]);
            }
        } catch (e) {
            setBackendError(
                "Error: " + (e.message ?? "Error al guardar la informacion")
            );
        }
        setIsLoading(false);
        handleCloseModal();
    }, [guest]);

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <TextInput
                label="Nombre"
                error={nameError}
                placeholder="Nombre"
                className=""
                inputClassNaem=""
                labelClassName=""
                errorClassName=""
                register={register}
                name="name"
                required
                maxLength={25}
                onChange={handleGuestChange}
                pattern={NAME_PATTERN}
            />
            <TextInput
                label="Apellido"
                error={surnameError}
                placeholder="Apellido"
                className=""
                inputClassName=""
                labelClassName=""
                errorClassName=""
                register={register}
                name="surname"
                required
                maxLength={25}
                onChange={handleGuestChange}
                pattern={SURNAME_PATTERN}
            />
            <TextInput
                label="email"
                error={emailError}
                placeholder="email"
                className=""
                inputClassName=""
                labelClassName=""
                errorClassName=""
                register={register}
                name="email"
                required
                maxLength={100}
                onChange={handleGuestChange}
                pattern={EMAIL_PATTERN}
            />
            <TextInput
                label="Telefono"
                error={phoneNumberError}
                placeholder="Telefono"
                className=""
                inputClassName=""
                labelClassName=""
                errorClassName=""
                register={register}
                name="phoneNumber"
                required
                maxLength={10}
                onChange={handleGuestChange}
                pattern={PHONE_NUMBER_PATTERN}
            />
            <br />
            {!!backendError && (
                <p className="form-backend-error centered">{backendError}</p>
            )}
            <br />
            <Button className="form-submit-btn centered" isSubmit>
                Guardar
            </Button>
        </form>
    );
};

export default GuestForm;
