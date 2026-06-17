![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Render](https://img.shields.io/badge/Render-%2346E3B7.svg?style=for-the-badge&logo=render&logoColor=white)

# Mundial 2026 Prediction Web App — Backend (losUrbinaBE)

This repository contains the robust backend API for the Mundial 2026 tournament predictor app. Built with **Java and Spring Boot**, this microservice manages users, match predictions, official server results, and calculates leaderboards in real time.

## 🚀 Tech Stack & DevOps Architecture
* **Language:** Java 17
* **Framework:** Spring Boot (JPA/Hibernate, Spring Web)
* **Database:** MySQL 8 (Hosted on Aiven.io Cloud Services)
* **Deployment/PaaS:** Render
* **Build Tool:** Maven

## 🛠️ Environment Variables Required
To run this project securely in production (Render) or locally without exposing credentials, make sure to **(**set up** / configurar)** the following variables:

* `PORT`: The port where the Tomcat server runs (Defaults to `8080`).
* `DB_HOST`: The Aiven MySQL database host URL.
* `DB_PORT`: The connection port (e.g., `13306`).
* `DB_NAME`: Database name (e.g., `defaultdb`).
* `DB_USER`: Database administrator username.
* `DB_PASSWORD`: Secure database password.

## 📦 How to Run Locally

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/camiloederson/losUrbinaBE.git](https://github.com/your-username/losUrbinaBE.git)
   cd losUrbinaBE
