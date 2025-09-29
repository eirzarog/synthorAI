# Synthor - A Chat GPT clone application 

This project is an AI-powered RESTful API web services application built with Spring Boot (backend) and React/Next.js (frontend). 

## Screenshots

### Desktop Interface  
<div align="center">
  <img width="1361" height="825" alt="Navbar" src="https://github.com/user-attachments/assets/92d793dd-7033-491a-99d2-819d4c558805" />
  <i>Main chat interface</i>
</div>

### User Authentication
<div align="center">
  <img width="1357" height="824" alt="LogIn" src="https://github.com/user-attachments/assets/4964ac6d-9871-4758-bb24-2b0955c5a531" />
  <i>Login form</i>
</div>
<br><br>
<div align="center">
  <img width="1357" height="821" alt="SignUp" src="https://github.com/user-attachments/assets/557fd595-59ad-4911-9138-e6ac103e229b" />
  <i>Registration form</i>
</div>

### Profile Modal
<div align="center">
  <img width="1350" height="821" alt="MainPage" src="https://github.com/user-attachments/assets/74adf7a4-636a-4c32-a1a4-e25f41665bcd" />
  <i>Modal menu for user settings and logout option</i>
</div>
<br><br>
<div align="center">
  <img width="1354" height="820" alt="Settings" src="https://github.com/user-attachments/assets/ffec852c-a973-45af-9bc4-dd5b3d7f0a7c" />
  <i>Settings modal with account management and security options</i>
</div>
<br>


## Tech Stack

### Backend
| Technology | Purpose | Version |
|------------|---------|---------|
| ![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white) | Programming Language | 21 |
| ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) | Backend Framework | 3.4.5 |
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) | Build & Dependency Management | Apache Maven |
| ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white) | Database | 42.7.3 |
| ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white) | Token-Based Auth | 0.11.5 |
| ![MapStruct](https://img.shields.io/badge/MapStruct-FF6F00?style=for-the-badge&logo=java&logoColor=white) | Object Mapping | 1.6.2 |
| ![Lombok](https://img.shields.io/badge/Lombok-FFA500?style=for-the-badge&logo=lombok&logoColor=white) | Annotation Processor | latest |
| ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white) | API Testing Tool | latest |

 

### Frontend
| Technology | Purpose | Version |
|------------|---------|---------|
| ![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB) | UI Framework | 18.2.0 |
| ![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white) | HTTP Client | latest |
| ![Next.js](https://img.shields.io/badge/Next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white) | Frontend Framework | latest |
| ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black) | Programming Language | ES2023 |
| ![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white) | Markup Language | 5 |
| ![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white) | Styling | 3 |
| ![Flexbox](https://img.shields.io/badge/Flexbox-1572B6?style=for-the-badge&logo=css3&logoColor=white) | Layout System | CSS3 |


## Features Breakdown

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
- [x] **Error Handling** - Graceful error states


## Getting started

### Backend Setup  

### Configure PostgreSQL

- Create a database (e.g., synthor_db) and apply the migration scripts located in the migrations directory.
- Update src/main/resources/application.properties:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/synthor_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Frontend Setup  
```bash
# Clone the repository
git clone https://github.com/eirzarog/synthorAI.git

# Navigate to project directory
cd frontend

# Install dependencies
npm install

# Run in development mode
npm run dev
```   

### Access the app
Backend API: http://localhost:8080<br>
Frontend: http://localhost:3000

 

