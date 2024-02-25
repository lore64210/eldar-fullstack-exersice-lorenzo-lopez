const Button = ({
    children,
    className = "",
    onClick,
    isSubmit = false,
    disabled = false,
}) => {
    return (
        <>
            {isSubmit ? (
                <input
                    type="submit"
                    className={`${className} btn`}
                    disabled={disabled}
                    value="Submit"
                />
            ) : (
                <button
                    type="button"
                    className={`${className} btn`}
                    onClick={onClick}
                    disabled={disabled}
                >
                    {children}
                </button>
            )}
        </>
    );
};

export default Button;
