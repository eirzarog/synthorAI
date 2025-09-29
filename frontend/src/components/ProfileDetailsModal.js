import { useState } from "react";

export default function ProfileDetailsModal({ closeModal }) {
    const [tab, setTab] = useState("account");

    return (
        <div className="profile-details-modal">
            <div className="modal-header">
                <h2>Settings</h2>
                <button onClick={closeModal}>X</button>
            </div>

            <div className="modal-tabs">
                <button
                    className={tab === "account" ? "active" : ""}
                    onClick={() => setTab("account")}
                >
                    Account
                </button>
                <button
                    className={tab === "security" ? "active" : ""}
                    onClick={() => setTab("security")}
                >
                    Security
                </button>
            </div>

            <div className="modal-content">
                {tab === "account" && (
                    <div>
                        <h3>Account Settings</h3>
                        <p>Change your profile information here.</p>
                    </div>
                )}
                {tab === "security" && (
                    <div>
                        <h3>Security Settings</h3>
                        <p>Update your password and 2FA here.</p>
                    </div>
                )}
            </div>
        </div>
    );
}
