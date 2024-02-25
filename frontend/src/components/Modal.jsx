import { forwardRef, useCallback } from "react";
import Button from "./forms/Button";
import Loading from "./Loading";
import { AiOutlineClose } from "react-icons/ai";

const Modal = forwardRef(
    (
        {
            children,
            className = "",
            contentClassName = "",
            onClose,
            title,
            onSubmit,
            submitButtonText,
            isLoading,
        },
        dialogRef
    ) => {
        const handleDialogClose = useCallback(
            (event) => {
                const rect = event.target.getBoundingClientRect();
                if (
                    !isLoading &&
                    (rect.left > event.clientX ||
                        rect.right < event.clientX ||
                        rect.top > event.clientY ||
                        rect.bottom < event.clientY)
                ) {
                    onClose?.();
                }
            },
            [onClose]
        );

        const handleDialogCloseFromButton = useCallback(() => {
            if (!isLoading) {
                onClose?.();
                dialogRef.current.close();
            }
        }, [onClose, dialogRef]);

        return (
            <dialog
                ref={dialogRef}
                className={`dialog ${className}`}
                onClick={handleDialogClose}
            >
                {isLoading && <Loading className="dialog-loading" />}
                <h2>{title}</h2>
                <div className={`dialog-content ${contentClassName}`}>
                    {children}
                </div>
                <AiOutlineClose
                    className="dialog-close-btn "
                    onClick={handleDialogCloseFromButton}
                    disabled={isLoading}
                />
                {onSubmit && !isLoading && (
                    <Button className="modal-submit-btn" onClick={onSubmit}>
                        {submitButtonText ? submitButtonText : "Confirmar"}
                    </Button>
                )}
            </dialog>
        );
    }
);

export default Modal;
