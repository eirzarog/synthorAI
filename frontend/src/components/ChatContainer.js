import { useState } from "react";

export default function ChatContainer() {
    const [messages, setMessages] = useState([
        { from: "user", text: "Hello AI!" },
        { from: "ai", text: "Hi! How can I help you today?" },
    ]);
    const [input, setInput] = useState("");

    const sendMessage = () => {
        if (!input.trim()) return;
        setMessages([...messages, { from: "user", text: input }]);
        setInput("");
        // You can add AI response logic here later
    };

    return (
        <div className="chat-container">
            <div className="chat-messages">
                {messages.map((m, idx) => (
                    <div
                        key={idx}
                        className={`message ${m.from === "user" ? "user-message" : "ai-message"}`}
                    >
                        {m.text}
                    </div>
                ))}
            </div>

            <div className="chat-input">
                <input
                    type="text"
                    placeholder="Type your message..."
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    onKeyDown={(e) => e.key === "Enter" && sendMessage()}
                />
                <button onClick={sendMessage}>Send</button>
            </div>
        </div>
    );
}
