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

const GuestForm = ({ onSubmit, guest, setGuest }) => {
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm({
        defaultValues: {
            name: guest?.name || "",
            surname: guest?.surname || "",
            surname: guest?.email || "",
            phoneNumber: guest?.phoneNumber || "",
        },
    });

    const { nameError, surnameError, emailError, phoneNumberError } =
        useFormError(errors);

    const handleGuestChange = ({ name, value }) => {
        setGuest({ ...guest, [name]: value });
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)} className="login-container">
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
                maxLength={255}
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
                maxLength={25}
                onChange={handleGuestChange}
                pattern={PHONE_NUMBER_PATTERN}
            />
            <Button className="" isSubmit>
                Guardar
            </Button>
        </form>
    );
};

export default GuestForm;
