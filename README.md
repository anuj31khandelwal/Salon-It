# Salon-It

![image](https://github.com/user-attachments/assets/a2fcdd6f-4334-46dd-9ff6-aeb640830d3a)

![image](https://github.com/user-attachments/assets/052e675d-9870-495b-bc87-a091c0fb6f8f)

![image](https://github.com/user-attachments/assets/0bd9db03-ea3f-462a-bc21-45b3bc356cbd)

**Salon-It** is a web application designed for salons to manage their services and appointments, while allowing customers to search for salons or specific services. The project is structured similarly to how Zomato shows restaurants and dishes, but instead of food, it lists salon services like beard styling, facials, and more. The platform supports multiple salons and allows users to book, manage, and cancel appointments.

## Features

### Customer:
- **Search for salons or services**: Customers can search for nearby salons or specific services.
- **View salon details**: Customers can see available services, costs, and salon availability.
- **Book appointments**: Customers can book appointments for their desired services.
- **Cancel appointments**: Customers can cancel appointments up to 20 minutes before the scheduled time.

### Salon Admin:
- **Register a salon**: Salon owners can register their salon on the platform.
- **Manage services**: Add, update, or delete services along with their costs and availability.
- **View scheduled appointments**: View all customer appointments and manage them.
- **Approve or reject appointments**: Confirm or reject customer appointments.

### Super Admin:
- **Manage salons**: View, approve, or reject salon registrations.

## Project Structure
![image](https://github.com/user-attachments/assets/300d5cd1-03e1-4ec9-8299-aff5d78c901b)

![image](https://github.com/user-attachments/assets/374f39de-4109-4f06-876c-a64cfd2add4a)

## Technologies Used
- **Frontend**: React.js
- **Backend**: Java, Spring Boot
- **Database**: MySQL
- **Version Control**: Git, GitHub
- **Tools**: Postman, VS Code
- **Deployment**: (Add deployment information if applicable)

## Setup and Installation

### Prerequisites
- Java 17
- Node.js & npm
- MySQL

### Backend Setup
1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/salon-it.git
    ```
2. Navigate to the backend directory:
    ```bash
    cd backend
    ```
3. Install dependencies and build the project:
    ```bash
    ./mvnw clean install
    ```
4. Update the `application.properties` file with your MySQL database credentials.
5. Run the backend server:
    ```bash
    ./mvnw spring-boot:run
    ```

### Frontend Setup
1. Navigate to the frontend directory:
    ```bash
    cd frontend
    ```
2. Install dependencies:
    ```bash
    npm install
    ```
3. Start the React development server:
    ```bash
    npm start
    ```

## API Endpoints

### Customer
- `GET /salons`: Retrieve a list of all salons.
- `GET /services`: Retrieve a list of all services.
- `POST /appointments`: Book a new appointment.
- `DELETE /appointments/{id}`: Cancel an appointment.

### Salon Admin
- `POST /salons`: Register a new salon.
- `POST /services`: Add a new service.
- `GET /appointments`: View all scheduled appointments.
- `PUT /appointments/{id}`: Confirm or reject an appointment.

### Super Admin
- `GET /salons`: View all salon registrations.
- `PUT /salons/{id}`: Approve or reject salon registrations.


