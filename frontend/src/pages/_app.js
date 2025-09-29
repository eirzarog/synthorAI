import "@/styles/globals.css";
import Sidebar from "@/components/Sidebar";
import Navbar from "@/components/Navbar";
import ChatContainer from "@/components/ChatContainer";
import Footer from "@/components/Footer";

export default function App() {
    return (
        <>

            <div style={{ display: 'flex', height: '100vh' }}>
            {/* Sidebar */}
           <Sidebar/>
            {/* Main content */}

            <div className="main">
                {/* navbar, chat, footer */}
                <Navbar/>
                <ChatContainer/>
                <Footer/>
            </div>
        </div>
        </>
    );



}
