import { useContext, useState } from "react";
import { AuthContext } from "@/context/AuthContext";
import ProfileModal from "@/components/ProfileModal";

export default function Navbar() {
    // const { isLoggedIn, logout } = useContext(AuthContext);
    const [showProfileModal, setShowProfileModal] = useState(false);
const isLoggedIn = false; //////
    return (
        <div className="navbar">
            <select>
                <option>GPT-3.5</option>
                <option>GPT-4</option>
            </select>

            <div id="authArea">
                {isLoggedIn ? (
                    <button onClick={() => setShowProfileModal(!showProfileModal)}>
                        Profile
                    </button>
                ) : (
                    <button onClick={() => (window.location.href = "/login")}>
                        Sign in
                    </button>
                )}
            </div>

            {showProfileModal && (
                <ProfileModal closeModal={() => setShowProfileModal(false)} />
            )}
        </div>
    );
}
