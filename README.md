# Full-Stack Blog Platform

This project is a full-stack blog platform developed in accordance with modern web standards, featuring a layered architecture. The system integrates a robust Java Spring Boot backend architecture with a modern React frontend user interface powered by NextUI.

---

## Technologies Used

### Backend
* **Language:** Java 21
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security & JWT (JSON Web Token)
* **Database:** PostgreSQL
* **Data Mapping:** MapStruct (DTO <-> Entity Conversions)
* **Data Management:** Spring Data JPA & Hibernate

### Frontend
* **Library/Framework:** React (with TypeScript)
* **Build Tool:** Vite
* **UI Library:** NextUI
* **Network Management:** Axios (with a centralized interceptor architecture)
* **Icons:** Lucide React

---

## Project Architecture and Folder Structure

The project is designed in a Monorepo structure to ensure seamless and centralized management of both backend and frontend development processes:

Blog/
├── BlogBackend/      # Spring Boot API Project (Port: 8080)
│   ├── src/main/     # Java Source Code, Controllers, and Business Logic
│   └── pom.xml       # Maven Dependency Management
├── frontend/         # Vite + React UI Project (Port: 5173)
│   ├── src/pages/    # Category, Post, and User Pages
│   ├── src/services/ # API Communication Bridge (apiService.ts)
│   └── package.json  # Node.js Dependency Management
└── .gitignore        # Global Git Filter File
Security and Business Logic Features
Strict Security Firewall: Role and method-based authorization are configured at the Spring Security layer. Guest users are restricted to read-only (GET) access, while content creation and the admin panel are fully secured.

Token Automation (Interceptor): On the frontend, an Axios interceptor captures outgoing requests and automatically attaches the authenticated user's JWT Token to the HTTP Authorization Header. If a token expires (resulting in a 401 error), the system securely redirects the user to the Login page.

Data-Level Security: A validation mechanism implemented in the PostServiceImpl layer ensures that a post owned by one user cannot be updated (PUT) or modified by another user, enforcing security at both the database and session levels.

Data Integrity Protection: The ability to delete categories that contain active posts is dynamically disabled directly in the frontend user interface. This proactive measure prevents database-level Foreign Key constraint violations before an invalid request is ever sent to the backend.

Installation and Setup
1. Clone the Repository
Bash
git clone [https://github.com/thresn/Blog-.git](https://github.com/thresn/Blog-.git)
cd Blog
2. Running the Backend
Ensure your local PostgreSQL server is running, create a database suitable for your configuration, and update your application.properties settings accordingly.

Bash
cd BlogBackend
./mvnw spring-boot:run
3. Running the Frontend
Bash
cd ../frontend
npm install
npm run dev
Open your browser and navigate to http://localhost:5173 to start using the platform.
