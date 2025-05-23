# 🗓️ Calendar Conflict Optimizer

A Spring Boot application that detects overlapping calendar events, suggests conflict-free time slots, and respects user working hours and time zones. Built with best practices for REST API design, security, and deployability.

---

## 🚀 Features

- ✅ Detects overlapping calendar events
- 🕐 Suggests available free slots within working hours
- 🌍 Time zone-aware scheduling
- 🔐 Secured using Keycloak (OpenID Connect)
- 🐘 PostgreSQL database (Dockerized)
- 🧪 Integration-ready with Testcontainers
- 📦 Clean Architecture (SOLID, layered structure)
- 🐳 Docker & Docker Compose support

---

## 📦 Technologies

- Java 17
- Spring Boot 3.x (REST, Security, JPA)
- PostgreSQL
- Keycloak (OIDC)
- Docker & Docker Compose
- Testcontainers (integration testing)

---

## ⚙️ Getting Started

### Prerequisites

- [Docker & Docker Compose](https://docs.docker.com/compose/install/)
- [Java 17+](https://adoptium.net)
- [Maven](https://maven.apache.org/)

### 🔧 Setup & Run

1. **Clone the repository:**

   git clone https://github.com/your-username/calendar-conflict-optimizer.git
   cd calendar-conflict-optimizer

2. **Start the services:**
   docker-compose up

