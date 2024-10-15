Salon-It
Salon-It is a web application designed for salons to manage their services and appointments, while allowing customers to search for salons or specific services. The project is structured similarly to how Zomato shows restaurants and dishes, but instead of food, it lists salon services like beard styling, facials, and more. The platform supports multiple salons and allows users to book, manage, and cancel appointments.

Features
Customer:
Search for salons or services: Customers can search for nearby salons or specific services.
View salon details: Customers can see available services, costs, and salon availability.
Book appointments: Customers can book appointments for their desired services.
Cancel appointments: Customers can cancel appointments up to 20 minutes before the scheduled time.
Salon Admin:
Register a salon: Salon owners can register their salon on the platform.
Manage services: Add, update, or delete services along with their costs and availability.
View scheduled appointments: View all customer appointments and manage them.
Approve or reject appointments: Confirm or reject customer appointments.
Super Admin:
Manage salons: View, approve, or reject salon registrations.
Project Structure
css
Copy code
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.demo
│   │   │       ├── controllers
│   │   │       │   ├── CustomerController.java
│   │   │       │   ├── SalonController.java
│   │   │       │   └── SuperAdminController.java
│   │   │       ├── models
│   │   │       │   ├── Appointment.java
│   │   │       │   ├── Salon.java
│   │   │       │   └── Service.java
│   │   │       ├── repositories
│   │   │       │   ├── AppointmentRepository.java
│   │   │       │   ├── SalonRepository.java
│   │   │       │   └── ServiceRepository.java
│   │   │       ├── services
│   │   │       │   ├── CustomerService.java
│   │   │       │   ├── SalonService.java
│   │   │       │   └── SuperAdminService.java
│   │   ├── resources
│   │   │   ├── application.properties
│   └── test

Technologies Used
Frontend: React.js
Backend: Java, Spring Boot
Database: MySQL
Version Control: Git, GitHub
Tools: Postman, VS Code
Deployment: (Add deployment information if applicable)
Setup and Installation
Prerequisites
Java 17
Node.js & npm
MySQL
Backend Setup
Clone the repository:
bash
git clone https://github.com/your-username/salon-it.git
Navigate to the backend directory:
bash
cd backend
Install dependencies and build the project:
bash
./mvnw clean install
Update the application.properties file with your MySQL database credentials.
Run the backend server:
bash
./mvnw spring-boot:run
Frontend Setup
Navigate to the frontend directory:
bash
cd frontend
Install dependencies:
bash
npm install
Start the React development server:
bash
npm start
API Endpoints
Customer
GET /salons: Retrieve a list of all salons.
GET /services: Retrieve a list of all services.
POST /appointments: Book a new appointment.
DELETE /appointments/{id}: Cancel an appointment.
Salon Admin
POST /salons: Register a new salon.
POST /services: Add a new service.
GET /appointments: View all scheduled appointments.
PUT /appointments/{id}: Confirm or reject an appointment.
Super Admin
GET /salons: View all salon registrations.
PUT /salons/{id}: Approve or reject salon registrations.
