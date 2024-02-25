import { useCallback } from "react";

const TextInput = ({
    label,
    error,
    placeholder,
    className = "",
    inputClassName = "",
    labelClassName = "",
    errorClassName = "",
    register,
    name,
    required = false,
    maxLength = null,
    defaultValue,
    onChange,
    pattern,
    type = "text",
}) => {
    const { onChange: formOnChange, ...inputProps } = register(name, {
        required,
        pattern,
        maxLength,
    });

    const handleOnChange = useCallback(
        (e) => {
            const {
                target: { value },
            } = e;
            onChange?.({ name, value });
            formOnChange(e);
        },
        [onChange, formOnChange, name]
    );

    return (
        <>
            <div className={`${className} input-container`}>
                <label>
                    <input
                        type={type}
                        className={`${inputClassName} input`}
                        placeholder={placeholder}
                        defaultValue={defaultValue}
                        {...inputProps}
                        onChange={handleOnChange}
                    />
                    <span className={`${labelClassName} label`}>{label}</span>
                </label>
            </div>
            {error && (
                <span className={`${errorClassName} input-error`}>{error}</span>
            )}
        </>
    );
};

export default TextInput;
