import { useCallback } from "react";

const TextInput = ({
    label,
    error,
    placeholder,
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
            <div className="input-container">
                <label>
                    <input
                        type={type}
                        className="input"
                        placeholder={placeholder}
                        defaultValue={defaultValue}
                        {...inputProps}
                        onChange={handleOnChange}
                    />
                    <span className="label">{label}</span>
                </label>
            </div>
            {error && <span className="input-error">{error}</span>}
        </>
    );
};

export default TextInput;
