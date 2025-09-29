# Synthor AI - A Chat GPT clone application 

A modern, AI-powered chat application with a sleek dark interface. Experience seamless conversations with advanced AI models in a responsive, user-friendly environment.

## Screenshots

### Desktop Interface 

<div align="center"><img width="1350" height="821" alt="MainPage" src="https://github.com/user-attachments/assets/74adf7a4-636a-4c32-a1a4-e25f41665bcd"  alt="Desktop Screenshot" width="280"/>
*Main chat interface showing the dark theme with sidbar navigation and conversation area*
</div>

### Settings configuration panel
<img width="1354" height="820" alt="Settings" src="https://github.com/user-attachments/assets/ffec852c-a973-45af-9bc4-dd5b3d7f0a7c" />
*Settings modal with account management and security options*

### Authentication Flow
![Auth Screenshot](https://via.placeholder.com/600x400/2563eb/ffffff?text=Login+%26+Registration)
<img width="1357" height="824" alt="LogIn" src="https://github.com/user-attachments/assets/4964ac6d-9871-4758-bb24-2b0955c5a531" />
<img width="1357" height="821" alt="SignUp" src="https://github.com/user-attachments/assets/557fd595-59ad-4911-9138-e6ac103e229b" />


*Secure authentication system with modern UI/UX design*


## Tech Stack

### Backend

### Frontend
| Technology | Purpose | Version |
|------------|---------|---------|
| ![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB) | UI Framework | 18.2.0 |
| ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black) | Programming Language | ES2023 |
| ![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white) | Markup Language | 5 |
| ![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white) | Styling | 3 |
| ![Flexbox](https://img.shields.io/badge/Flexbox-1572B6?style=for-the-badge&logo=css3&logoColor=white) | Layout System | CSS3 |

 
 

## 🏗️ Architecture

![Architecture Diagram](https://via.placeholder.com/900x600/7c3aed/ffffff?text=Component+Architecture+%26+Data+Flow)

### Project Structure
```
synthor-chat/
├── src/
│   ├── components/
│   │   ├── Chat/
│   │   │   ├── ChatWindow.js
│   │   │   ├── MessageBubble.js
│   │   │   └── InputArea.js
│   │   ├── Sidebar/
│   │   │   ├── Sidebar.js
│   │   │   └── ConversationList.js
│   │   ├── Modals/
│   │   │   ├── AuthModal.js
│   │   │   ├── ProfileModal.js
│   │   │   └── SettingsModal.js
│   │   └── Common/
│   │       ├── Navbar.js
│   │       └── Footer.js
│   ├── hooks/
│   │   ├── useAuth.js
│   │   ├── useLocalStorage.js
│   │   └── useChat.js
│   ├── utils/
│   │   ├── storage.js
│   │   └── helpers.js
│   ├── styles/
│   │   ├── globals.css
│   │   ├── components.css
│   │   └── themes.css
│   └── App.js
├── public/
├── package.json
└── README.md
```

### Component Hierarchy
- **App** (Root Container with Flexbox Layout)
  - **Sidebar** (Fixed width, vertical flex)
    - Navigation Items
    - Conversation History
    - Upgrade Button
  - **Main Content** (Flex: 1, vertical layout)
    - **Navbar** (Model selector, Auth controls)
    - **Chat Container** (Flex: 1, scrollable)
      - Message History
      - Input Area
    - **Footer** (Fixed height, legal links)

## ✨ Key Features

- 🎨 **Modern Dark Theme** - Carefully crafted dark UI with excellent contrast
- 📱 **Fully Responsive** - Seamless experience across desktop, tablet, and mobile
- 🔐 **Authentication System** - Secure login/logout with persistent sessions
- 🗂️ **Conversation Management** - Organized chat history and session management
- ⚙️ **Advanced Settings** - Comprehensive user profile and security settings
- 🔄 **State Persistence** - LocalStorage integration for seamless user experience
- 
## 🎨 Design System

### Layout System
- **Flexbox-based** responsive grid

## Quick Start

### Prerequisites
```bash
node >= 16.0.0
npm >= 8.0.0
Modern web browser (Chrome, Firefox, Safari, Edge)
```

### Installation
```bash
# Clone the repository
git clone https://github.com/yourusername/synthor-chat.git

# Navigate to project directory
cd synthor-chat

# Install dependencies
npm install

# Start development server
npm start
```

### Development Server
```bash
# Run in development mode
npm run dev

# Build for production
npm run build

# Run production build locally
npm run preview
```

### Environment Setup
```bash
# Copy environment template
cp .env.example .env.local

# Add your API keys and configuration
REACT_APP_API_URL=http://localhost:3001
REACT_APP_AI_MODEL=gpt-3.5-turbo
```
 
## 🔧 Configuration

### Customization Options
```javascript
// src/config/app.js
export const APP_CONFIG = {
  theme: 'dark',
  sidebar: {
    width: '280px',
    collapsible: true
  },
  chat: {
    maxMessages: 100,
    autoScroll: true,
    messageDelay: 600
  },
  auth: {
    persistSession: true,
    sessionTimeout: 24 // hours
  }
};
```
 
## 📊 Features Breakdown

### Core Functionality
- [x] **Chat Interface** - Real-time messaging with AI
- [x] **User Authentication** - Login/logout with persistence
- [x] **Profile Management** - User settings and preferences
- [x] **Conversation History** - Message storage and retrieval
- [x] **Responsive Design** - Mobile and desktop optimization
- [x] **Dark Theme** - Modern, eye-friendly interface

### Advanced Features
- [x] **Modal System** - Reusable modal components
- [x] **File Upload** - Profile picture management
- [x] **Form Validation** - Client-side input validation
- [x] **Session Management** - Persistent login state
- [x] **Accessibility** - Keyboard navigation and ARIA labels
- [x] **Error Handling** - Graceful error states

 
