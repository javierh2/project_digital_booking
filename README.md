
# 🏨 Digital Booking — Room Hotel
### Full Stack Web Application
**Sprint 1 Implementation — Backend + Frontend**  
Febrero — Marzo 2026

---

# 🚀 Overview

**Digital Booking – Room Hotel** is a full‑stack web application that simulates a hotel room booking platform.

The project was developed during **Sprint 1**, implementing both:

- ⚙️ Backend REST API with **Java + Spring Boot**
- 💻 Frontend SPA with **React + Vite**
- 🔗 Real **end‑to‑end communication** between frontend and backend
- 🧪 API and UI testing

This repository documents the architecture, features, and implementation of the system.

---

# 🧱 Tech Stack

## Backend
- Java 21
- Spring Boot 3.2.3
- Spring Data JPA
- Hibernate
- H2 Database
- Lombok

## Frontend
- React 19
- Vite
- React Router DOM

## Testing
- Postman
- Manual component testing

## Tools
- Git
- GitHub
- IntelliJ IDEA
- Visual Studio Code

---

# 📊 Sprint Summary

During Sprint 1, **11 User Stories** were implemented covering core platform functionality including:

- UI layout
- Room listing
- Room detail view
- Admin panel
- API integration
- Pagination
- Product creation and deletion

| Sprint | Period |
|------|------|
| Sprint 1 | Febrero – Marzo 2026 |

---

# 🎨 Brand Identity

## Logo

The **Digital Booking** logo uses the initials **DB** with a navy background representing a modern digital platform.

## Color Palette

| Color | Code | Usage |
|-----|-----|-----|
Cream | #F9F3EE | Main background |
Navy | #2C3E50 | Header / text |
Terracotta | #C47E5A | Primary actions |
Terracotta Dark | #B06A45 | Button hover |
Gray | #9E8E82 | Secondary text |
Blush | #EFE4D9 | Borders / placeholders |

---

# 📋 User Stories Implemented

| ID | Feature |
|---|---|
HU1 | Global Header |
HU2 | Home Page Layout |
HU3 | Create Room |
HU4 | Random Rooms |
HU5 | Room Detail |
HU6 | Image Gallery |
HU7 | Global Footer |
HU8 | Pagination |
HU9 | Admin Panel |
HU10 | List Rooms |
HU11 | Delete Room |

---

# ⚙️ Backend Architecture

```
com.roomhotel.roomhotel

config/
controller/
dto/
entity/
exception/
repository/
service/
```

### Key Responsibilities

**Controller**
Handles HTTP requests.

**Service**
Contains business logic.

**Repository**
Database access via JPA.

**DTO**
Transfer objects between frontend and backend.

**Entity**
Database model.

---

# 🔌 REST API

| Method | Endpoint | Description |
|------|------|------|
GET | /api/rooms | List all rooms |
GET | /api/rooms/random | Random rooms |
GET | /api/rooms/{id} | Room detail |
POST | /api/rooms | Create room |
DELETE | /api/rooms/{id} | Delete room |

---

# 💻 Frontend Architecture

```
src/

components/
Header
Footer
SearchBar
Categories
RoomCard
RoomForm
Pagination

pages/
Home
Admin
RoomDetail

services/
roomService.js
```

---

# 🧭 Application Routes

| Route | Page |
|------|------|
/ | Home |
/rooms/:id | Room Detail |
/admin | Admin Panel |
/administracion | Admin Panel (alt) |

---

# ⚛ React Hooks Used

| Hook | Purpose |
|---|---|
useState | Component state |
useEffect | API calls |
useNavigate | Navigation |
useParams | Dynamic route parameters |

---

# 🔄 End‑to‑End Data Flow

1️⃣ User interacts with UI  
2️⃣ React calls **roomService.js**  
3️⃣ HTTP request sent to backend  
4️⃣ Spring Controller receives request  
5️⃣ Service applies business logic  
6️⃣ Repository queries database  
7️⃣ Response returned as DTO  
8️⃣ React updates UI state

---

# 🧪 Testing

## API Testing

Performed using **Postman** verifying:

- HTTP response codes
- JSON structure
- Error handling
- Validation

Endpoints tested include:

- GET rooms
- GET random rooms
- GET room by id
- POST create room
- DELETE room

---

## Component Testing

Manual testing in development environment.

| Component | Result |
|---|---|
Header | ✔ Correct |
Footer | ✔ Correct |
SearchBar | ✔ Correct |
Categories | ✔ Correct |
RoomCard | ✔ Correct |
RoomForm | ✔ Correct |
Pagination | ✔ Correct |
Home | ✔ Correct |
Admin | ✔ Correct |
RoomDetail | ✔ Correct |

---

# 🧰 Development Workflow

The project follows a **professional Git workflow**.

| Tool | Purpose |
|---|---|
Git | Version control |
GitHub | Remote repository |
IntelliJ | Backend development |
VS Code | Frontend development |
Postman | API testing |

---

# 📦 Project Status

✅ Sprint 1 Completed  

Future work may include:

- Authentication system
- Room booking system
- Database migration to PostgreSQL
- Deployment
- Unit testing

---

# 👨‍💻 Author

Project developed as part of a **Full Stack development program**.

