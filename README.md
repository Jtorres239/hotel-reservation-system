# Hotel Reservation System

Baylor University – CS Project  
Author: Joseph Torres  

---

## 📌 Description
This project is a hotel reservation system for a small hotel with 40 rooms.  
It supports guest reservations, cancellations, profile management, check-in/check-out, and room management.

---

## 📌 Main Features
- **Make reservation (UC01)**
- **Cancel reservation (UC02)**
- Guest login and profile management
- Clerk login and room/guest management
- Admin account creation and password reset
- Room status view (available, reserved, occupied)
- Basic billing and reporting

---

## 📌 Technologies Used
- Java (IntelliJ IDEA)
- JUnit 5 (unit testing)
- No external libraries required
- Simple console-based interface

---

## 📌 Project Structure
\`\`\`
/src
   /main/java
       (all application classes)
   /test/java
       (JUnit tests for Reservation, Controller, etc.)

/docs
   (diagrams and deliverables for the project)
\`\`\`

---

## 📌 How to Run

### 1. Clone the repository:
\`\`\`bash
git clone https://github.com/Jtorres239/hotel-reservation-system.git
\`\`\`

### 2. Open in IntelliJ IDEA
- Go to **File → Open**
- Select the project folder  
- Allow IntelliJ to detect the project structure

### 3. Run the application
- Open the file containing the \`main\` method (e.g., \`HotelApp.java\`)
- Click **Run**

---

## 📌 How to Run Tests
- Open IntelliJ
- Right-click the \`test\` directory
- Select **Run All Tests**

or

- Run tests individually inside the test folder (ReservationTest, ControllerTest, etc.)

---

## 📌 Notes
- The system is designed with a 4-tier architecture (Presentation, Controller, Logic, Data).
- All diagrams and documentation required for deliverables are included in the \`/docs\` folder.
