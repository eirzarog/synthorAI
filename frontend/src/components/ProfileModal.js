import { useContext, useState } from "react";
import { AuthContext } from "@/context/AuthContext";
import ProfileDetailsModal from "@/components/ProfileDetailsModal";

export default function ProfileModal({ closeModal }) {
    const { logout } = useContext(AuthContext);
    const [showDetails, setShowDetails] = useState(false);

    return (
        <>
            <div className="profile-modal">
                <button onClick={() => setShowDetails(true)}>Settings</button>
                <button onClick={logout}>Logout</button>
            </div>

            {showDetails && (
                <ProfileDetailsModal closeModal={() => setShowDetails(false)} />
            )}
        </>
    );
}
