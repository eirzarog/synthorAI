export default function Sidebar() {
    return (
        <aside id="sidebar">
            <div style={{display:'flex', alignItems:'center', padding:'8px', fontWeight:'bold'}}>
                <span style={{flex:1, fontSize:'20px', color:'#fff'}}>Synthor</span>
            </div>
            <div>
                <div className="item">New chat</div>
                <div className="item">Search</div>
                <div style={{padding:'8px', fontWeight:'600', color:'#e5e7eb'}}>Conversations</div>
                <div className="item">Chat 1</div>
                <div className="item">Chat 2</div>
            </div>
            <div className="upgrade btn-primary">Upgrade</div>
        </aside>
    )
}