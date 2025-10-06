# MySQL Schema Design – Smart Clinic Management System

## 📋 Overview
This schema defines the database structure for the Smart Clinic Management System, covering Doctors, Patients, Appointments, and Prescriptions.

---

## 🧱 Tables

### 1. doctors
| Field | Type | Description |
|-------|------|--------------|
| id | INT (PK, AUTO_INCREMENT) | Unique doctor ID |
| name | VARCHAR(150) | Doctor’s full name |
| specialty | VARCHAR(100) | Area of specialization |
| email | VARCHAR(150, UNIQUE) | Doctor’s login email |
| password | VARCHAR(150) | Doctor’s password |

---

### 2. doctor_available_times
| Field | Type | Description |
|-------|------|--------------|
| doctor_id | INT (FK → doctors.id) | Doctor reference |
| available_time | TIME | Time slot available for appointments |

---

### 3. patients
| Field | Type | Description |
|-------|------|--------------|
| id | INT (PK, AUTO_INCREMENT) | Unique patient ID |
| name | VARCHAR(150) | Patient’s full name |
| email | VARCHAR(150, UNIQUE) | Email used for login |
| phone | VARCHAR(30) | Contact number |
| password | VARCHAR(150) | Patient’s password |

---

### 4. appointments
| Field | Type | Description |
|-------|------|--------------|
| id | INT (PK, AUTO_INCREMENT) | Appointment ID |
| doctor_id | INT (FK → doctors.id) | Associated doctor |
| patient_id | INT (FK → patients.id) | Associated patient |
| appointment_time | DATETIME | Scheduled time |
| notes | TEXT | Optional notes |

---

### 5. prescriptions
| Field | Type | Description |
|-------|------|--------------|
| id | INT (PK, AUTO_INCREMENT) | Prescription ID |
| appointment_id | INT (FK → appointments.id) | Appointment reference |
| medication | VARCHAR(500) | Medication details |
| notes | TEXT | Additional notes |
| prescribed_by | VARCHAR(150) | Doctor who issued the prescription |

---

## 🔗 Relationships
- One Doctor → Many Appointments  
- One Patient → Many Appointments  
- One Appointment → One Prescription  
- One Doctor → Many Available Time Slots  

---

## 🧩 Example SQL
```sql
CREATE DATABASE IF NOT EXISTS smartclinic;
USE smartclinic;

CREATE TABLE doctors (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150),
  specialty VARCHAR(100),
  email VARCHAR(150) UNIQUE,
  password VARCHAR(150)
);

CREATE TABLE doctor_available_times (
  doctor_id INT,
  available_time TIME,
  FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

CREATE TABLE patients (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150),
  email VARCHAR(150) UNIQUE,
  phone VARCHAR(30),
  password VARCHAR(150)
);

CREATE TABLE appointments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  doctor_id INT,
  patient_id INT,
  appointment_time DATETIME,
  notes TEXT,
  FOREIGN KEY (doctor_id) REFERENCES doctors(id),
  FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE prescriptions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  appointment_id INT,
  medication VARCHAR(500),
  notes TEXT,
  prescribed_by VARCHAR(150),
  FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
