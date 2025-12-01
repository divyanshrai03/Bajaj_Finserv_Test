# 🚀 Bajaj Finserv Health (Java)

This repository contains an automated Spring Boot application built as part of the **Bajaj Finserv Health Backend Hiring Challenge**.  
The application interacts directly with the official hiring API — generating a webhook, determining the assigned question, computing the corresponding SQL solution, and submitting it securely using a JWT token.

---

## Google drive for JAR - https://drive.google.com/file/d/1Ww7TwGLSrAflA4GKexqV9XLXY2VgOX80/view

## 📌 Project Workflow

The application executes automatically using `CommandLineRunner` with **no manual trigger and no REST controllers**.

It performs the following steps:

1. **📩 Generate Webhook & Access Token**  
   Sends a POST request to receive:
   - A unique webhook URL  
   - A JWT access token  

2. **🧠 Process Assigned Question**  
   Based on the last digits of the registration number, the assigned task was:  
   **➡️ Question 2 — High Earner Department & Aggregated Employee Analysis**

   The application generates the final SQL query dynamically and stores it internally.

3. **📤 Submit Final SQL Query**  
   The query is submitted to the webhook using:

Authorization: `eyJhbGciOiJIUzI1NiJ9.eyJyZWdObyI6IjIyQkxDMTEwNiIsIm5hbWUiOiJEaXZ5YW5zaF9SYWkiLCJlbWFpbCI6ImRpdnlhbnNoLnJhaTIwMjJAdml0c3R1ZGVudC5hYy5pbiIsInN1YiI6IndlYmhvb2stdXNlciIsImlhdCI6MTc2NDU4ODM1MCwiZXhwIjoxNzY0NTg5MjUwfQ.AaWNO7xzR5vC96Y8TBVXp-EozdgyEBuMr5J9heVtBmc`


---

## 🛠️ Tech Stack

| Component | Technology |
|----------|------------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.x |
| **HTTP Client** | RestTemplate |
| **Execution Style** | CommandLineRunner (Auto-run) |
| **Build Tool** | Maven Wrapper (`mvnw`) |
| **IDE Used** | VS Code |

---

## 🧠 Problem Assigned — Question 2

The challenge required:

- Identifying employees with **at least one payment > ₹70,000**
- Grouping results by **department**
- Limiting each department result to the **top 10 high earners**
- Computing the **average age** of those employees
- Creating a **comma-separated list of names**
- Sorting the final output by **department ID (DESC)**

---

## 🧾 Final SQL Query Submitted

```sql
WITH high_earners AS (
 SELECT DISTINCT
     d.department_id,
     d.department_name,
     e.emp_id,
     e.first_name,
     e.last_name,
     EXTRACT(YEAR FROM AGE(CURRENT_DATE, e.dob)) AS age
 FROM department d
 JOIN employee e
     ON e.department = d.department_id
 WHERE EXISTS (
     SELECT 1
     FROM payments p
     WHERE p.emp_id = e.emp_id
       AND p.amount > 70000
 )
),
ranked AS (
 SELECT
     high_earners.*,
     ROW_NUMBER() OVER (
         PARTITION BY department_id
         ORDER BY emp_id
     ) AS rn
 FROM high_earners
)
SELECT
 department_name,
 ROUND(AVG(age), 2) AS average_age,
 STRING_AGG(first_name || ' ' || last_name, ', ') AS employee_list
FROM ranked
WHERE rn <= 10
GROUP BY department_id, department_name
ORDER BY department_id DESC;
```

##▶️ Setup & Execution
```
# Clone the repository
git clone https://github.com/divyanshrai03/Bajaj_Finserv_Test.git
cd Bajaj_Finserv_Test

# Build project
.\mvnw.cmd clean package

# Run the application
.\mvnw.cmd spring-boot:run
```

The application will automatically:

  1) Generate a webhook

  2) Build and store the SQL query

  3) Submit it using the JWT token

  4) Print response in the console

##📂 Project Structure
```
src/main/java/com/bajaj/test
├── BajajTestApplication.java   # Main entry point containing the logic flow
└── dto                         # Data Transfer Objects
    ├── RegistrationRequest.java
    ├── RegistrationResponse.java
    └── SubmissionRequest.java
```

## Terminal Output
![WhatsApp Image 2025-12-01 at 17 01 37](https://github.com/user-attachments/assets/8d2bbf9a-e5d1-4a0a-8041-4c114b9d2448)
