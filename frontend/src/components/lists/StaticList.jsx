const StaticList = ({ title, guestList }) => {
    return (
        <div className="guest-list">
            <h2>{title}</h2>
            {guestList.map((guest) => (
                <div
                    key={guest.id}
                    className="list-item static-list-item space-between"
                >
                    <p className="list-item-text">
                        {guest.name} {guest.surname}
                    </p>
                </div>
            ))}
        </div>
    );
};

export default StaticList;
